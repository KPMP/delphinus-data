package org.kpmp.slides;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "patients")
public class Slide {

	@Id
	private String id;
	private String slideName;

	@Field("slideType")
	private String slideType;

	@Field("metadata")
    @JsonIgnore
	private Metadata metadata;

	@Field("stain")
	private Stain stain;

	@Field("removed")
	private boolean removed;

	public Stain getStain() {
		return stain;
	}

	public void setStain(Stain stain) {
		this.stain = stain;
	}

	public String getSlideName() {
		return slideName;
	}

	public void setSlideName(String slideName) {
		this.slideName = slideName;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSlideType() {
		return slideType;
	}

	public void setSlideType(String slideType) {
		this.slideType = slideType;
	}

	public boolean getRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

}
