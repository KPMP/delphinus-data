package org.kpmp.slides;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "patients")
public class Aperio {
	
	private String OriginalHeight;

	private String OriginalWidth;

	private String MPP;
	
	public String getOriginalHeight() {
		return OriginalHeight;
	}

	public void setOriginalHeight(String OriginalHeight) {
		this.OriginalHeight = OriginalHeight;
	}

	public String getOriginalWidth() {
		return OriginalWidth;
	}

	public void setOriginalWidth(String OriginalWidth) {
		this.OriginalWidth = OriginalWidth;
	}
	

	public String getMPP() {
		return MPP;
	}

	public void setMPP(String MPP) {
		this.MPP = MPP;
	}
	

}
