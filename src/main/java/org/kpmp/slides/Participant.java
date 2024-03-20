package org.kpmp.slides;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "patients")
public class Participant {

	@Field("kpmp_id")
	private String kpmpId;
	@Field("label")
	private String label;
	@Field("slides")
	List<Slide> slides = new ArrayList<>();

	List<Slide> slideList = new ArrayList<>();

	private Map<String, List<Slide>> slideMap = new HashMap<>();

	public Map<String, List<Slide>> getSlides() {

		for (Slide slide : this.slides) {
			List<Slide> newSlideList = new ArrayList<>();
			String slideType = slide.getSlideType();
			if (slideMap.containsKey(slideType)) {
				newSlideList = slideMap.get(slideType);
			}
			newSlideList.add(slide);
			slideMap.put(slideType, newSlideList);
		}
		return slideMap;
	}

	public String getKpmpId() {
		return kpmpId;
	}

	public void setKpmpId(String kpmpId) {
		this.kpmpId = kpmpId;
	}


	public void setSlides(Map<String, List<Slide>> slides) {
		this.slideMap = slides;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
