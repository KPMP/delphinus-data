package org.kpmp.slides;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "patients")
public class PatientSlides {

	@Field("kpmp_id")
	private String kpmpId;
	@Field("label")
	private String label;
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
