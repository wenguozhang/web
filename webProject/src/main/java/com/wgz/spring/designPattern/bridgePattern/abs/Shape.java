package com.wgz.spring.designPattern.bridgePattern.abs;

import com.wgz.spring.designPattern.bridgePattern.DrawAPI;

public abstract class Shape {
	protected DrawAPI drawAPI;

	protected Shape(DrawAPI drawAPI) {
		this.drawAPI = drawAPI;
	}

	public abstract void draw();
}