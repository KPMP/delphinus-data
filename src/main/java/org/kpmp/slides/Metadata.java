package org.kpmp.slides;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "patients")
public class Metadata {

	private List<Overlay> overlay;
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
