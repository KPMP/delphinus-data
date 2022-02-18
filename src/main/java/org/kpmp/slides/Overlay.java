package org.kpmp.slides;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "patients")
public class Overlay {

	private double px;
	private double py;
	private double width;
	private double height;
	private String className;
	@Field("id")
	private String id;

	public double getPx() {
		return px;
	}

	public void setPx(double px) {
		this.px = px;
	}

	public double getPy() {
		return py;
	}

	public void setPy(double py) {
		this.py = py;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
