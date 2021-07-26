package org.kpmp.slides;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "patients")
public class OpenSlide {
	
    private String mpp_x;
	private String mpp_y;

	public String getMpp_x() {
		return mpp_x;
	}

	public void setMpp_x(String mpp_x) {
		this.mpp_x = mpp_x;
	}
	
	public String getMpp_y() {
		return mpp_y;
	}

	public void setMpp_y(String mpp_y) {
		this.mpp_y = mpp_y;
	}
	
}
