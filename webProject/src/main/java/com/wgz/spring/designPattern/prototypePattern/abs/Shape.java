package com.wgz.spring.designPattern.prototypePattern.abs;

public abstract class Shape implements Cloneable {

	private String id;
	protected String type;

	public abstract void draw();

	public void setType(String type) {
		this.type = type;
	} 
	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Object clone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}
}