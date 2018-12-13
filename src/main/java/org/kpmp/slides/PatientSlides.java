package org.kpmp.slides;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "patients")
public class PatientSlides {

	@Field("kpmp_id")
	private String kpmpId;
	@Field("slides")
	List<Slide> slides = new ArrayList<>();

	public String getKpmpId() {
		return kpmpId;
	}

	public void setKpmpId(String kpmpId) {
		this.kpmpId = kpmpId;
	}

	public List<Slide> getSlides() {
		return slides;
	}

	public void setSlides(List<Slide> slides) {
		this.slides = slides;
	}

}
