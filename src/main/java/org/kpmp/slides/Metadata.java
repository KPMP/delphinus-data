package org.kpmp.slides;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "patients")
public class Metadata {

	// @Field("aperio")
	private Aperio aperio;
	private OpenSlide openslide;

	public Aperio getAperio() {
		return aperio;
	}

	public void setAperio(Aperio aperio) {
		this.aperio = aperio;
	}

	public OpenSlide getOpenSlide() {
		return openslide;
	}

	public void setOpenSlide(OpenSlide openslide) {
		this.openslide = openslide;
	}


}
