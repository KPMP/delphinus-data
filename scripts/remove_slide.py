import sys
from pymongo import MongoClient
import os


def remove_links(slide_id):
    targets = [
        f'/data/deepZoomImages/{slide_id}_files',
        f'/data/deepZoomImages/{slide_id}.dzi',
        f'/data/deepZoomImages/tn_{slide_id}.jpeg'
    ]
    for target in targets:
        if os.path.islink(target) or os.path.exists(target):
            try:
                os.remove(target)
                print(f"Removed link or file: {target}")
            except Exception as e:
                print(f"Failed to remove {target}: {e}")

def remove_if_block_from_linksh(slide_id, link_sh_path):
    if not os.path.exists(link_sh_path):
        print(f"link.sh not found at {link_sh_path}")
        return
    with open(link_sh_path, "r") as f:
        lines = f.readlines()

    new_lines = []
    inside_if = False
    block_buffer = []

    for line in lines:
        if line.strip().startswith("if ! [ -L "):
            inside_if = True
            block_buffer = [line]
            continue
        if inside_if:
            block_buffer.append(line)
            if line.strip() == "fi":
                block_str = "".join(block_buffer)
                if slide_id in block_str:
                    inside_if = False
                    block_buffer = []
                    continue
                else:
                    new_lines.extend(block_buffer)
                    inside_if = False
                    block_buffer = []
            continue
        new_lines.append(line)

    with open(link_sh_path, "w") as f:
        f.writelines(new_lines)
    print(f"Removed if block containing slide id '{slide_id}' from {link_sh_path}")
    

if len(sys.argv) < 2:
    print("Usage: python removeSlide.py <slideName>")
    sys.exit(1)

slide_name = sys.argv[1]
uri = "mongodb://localhost:27017"
db_name = "knowledgeEnvironment"
collection_name = "patients"
link_sh_path = "/data/deepZoomImages/link.sh"

client = MongoClient(uri)
db = client[db_name]
collection = db[collection_name]

query = {"slides.slideName": slide_name}
found_docs = list(collection.find(query))

if len(found_docs) == 1:
    for doc in found_docs:
        kpmp_id = doc.get("kpmp_id")
        slides = doc.get("slides", [])
        for slide in slides:
            if slide.get("slideName") == slide_name:
                slide_id = slide.get("_id")
                if kpmp_id and slide_id:
                    remove_links(slide_id)
                    remove_if_block_from_linksh(slide_id, link_sh_path)

                    update = {"$pull": {"slides": {"slideName": slide_name}}}
                    result = collection.update_many(query, update)
                    print(f"\nRemoved slide '{slide_name}' from {result.modified_count} document(s).")
elif len(found_docs) > 1:
    print(f"Error: Found multiple documents with slide name '{slide_name}'.")
else:
    print(f"No documents found with slide name '{slide_name}'.")
    
client.close()