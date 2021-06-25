package org.kpmp.slides;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "patients")
public class Metadata {

	private Aperio aperio;

	public Aperio getAperio() {
		return aperio;
	}

	public void setAperio(Aperio aperio) {
		this.aperio = aperio;
	}


}
