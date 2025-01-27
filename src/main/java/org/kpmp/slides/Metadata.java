package org.kpmp.slides;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "patients")
public class Metadata {

    @Field("overlay")
	private List<Overlay> overlay;
    @Field("overlayLabel")
	private List<String> overlayLabel;

	public List<Overlay> getOverlay() {
		return overlay;
	}

	public void setOverlay(List<Overlay> overlay) {
		this.overlay = overlay;
	}

	public List<String> getOverlayLabel() {
		return overlayLabel;
	}

	public void setOverlayLabel(List<String> overlayLabel) {
		this.overlayLabel = overlayLabel;
	}

}
