package com.wgz.spring.designPattern.abstractFactoryPattern.abs;

import com.wgz.spring.designPattern.abstractFactoryPattern.Color;
import com.wgz.spring.designPattern.abstractFactoryPattern.Shape;

public abstract class AbstractFactory {
	public abstract Color getColor(String color);

	public abstract Shape getShape(String shape);
}