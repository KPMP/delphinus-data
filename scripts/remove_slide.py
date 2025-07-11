from pymongo import MongoClient
import os
import argparse


class Main:
    def __init__(self):
        self.client, self.collection = self.create_connection()
        self.remove_if_block_from_linksh = self.remove_if_block_from_linksh
        self.remove_links = self.remove_links
        self.remove_slide = self.remove_slide
        self.remove_participant = self.remove_participant

    def create_connection(self):
        uri = "mongodb://localhost:27017"
        db_name = "knowledgeEnvironment"
        collection_name = "patients"
        
        client = MongoClient(uri)
        db = client[db_name]
        collection = db[collection_name]
        
        return client, collection

    def remove_links(self, slide_id):
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

    def remove_if_block_from_linksh(self, slide_id, link_sh_path):
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

    def remove_slide(self, slide_name):
        link_sh_path = "/data/deepZoomImages/link.sh"
        client, collection = self.create_connection()

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
                            self.remove_links(slide_id)
                            self.remove_if_block_from_linksh(slide_id, link_sh_path)

                            update = {"$pull": {"slides": {"slideName": slide_name}}}
                            result = collection.update_many(query, update)
                            print(f"\nRemoved slide '{slide_name}' from {result.modified_count} document(s).")
        elif len(found_docs) > 1:
            print(f"Error: Found multiple documents with slide name '{slide_name}'.")
        else:
            print(f"No documents found with slide name '{slide_name}'.")

        client.close()
        
    def remove_participant(self, participant_id):
        link_sh_path = "/data/deepZoomImages/link.sh"
        client, collection = self.create_connection()
        query = {"kpmp_id": participant_id}
        found_docs = list(collection.find(query))
        if len(found_docs) == 1:
            for doc in found_docs:
                    slides = doc.get("slides", [])
                    for slide in slides:
                        slide_id = slide.get("_id")
                        self.remove_links(slide_id)
                        self.remove_if_block_from_linksh(slide_id, link_sh_path)
            result = collection.delete_one(query)
            if result.deleted_count > 0:
                 print(f"Removed participant with kpmp_id '{participant_id}'.")
            else:
                print(f"Failed to remove participant from patients collection with kpmp_id '{participant_id}'.")
        elif len(found_docs) > 1:
            print(f"Error: Found multiple participants with kpmp_id '{participant_id}'.")
        
        elif len(found_docs) == 0:
            print(f"No participant found with kpmp_id '{participant_id}'.")
        client.close()

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument(
        "-s",
        "--slide_name",
        type=str,
        help="Name of the slide to remove without an extension (e.g., 'sample-id_HE_1of2' )",
    )
    parser.add_argument(
        "-a",
        "--action",
        choices=['remove-slide', 'remove-participant'],
        required=True,
        help="remove-slide: Remove a slide from the database and filesystem."
             "remove-participant: Remove a participant from the database.",
    )
    parser.add_argument(
        "-p",
        "--participant_id",
        type=str,
        help="kpmp_id of the participant to remove from the database.",
    )
    args = parser.parse_args()
    main = Main()
    if args.action == 'remove-slide':
        result = main.remove_slide(args.slide_name)
    elif args.action == 'remove-participant':
        result = main.remove_participant(args.participant_id)