package com.wgz.spring.designPattern.abstractFactoryPattern.impl;

import com.wgz.spring.designPattern.abstractFactoryPattern.Color;

public class Green implements Color {

	@Override
	public void fill() {
		System.out.println("Inside Green::fill() method.");
	}
}