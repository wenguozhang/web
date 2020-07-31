package com.wgz.spring.designPattern.adapterPattern.impl;

import com.wgz.spring.designPattern.adapterPattern.abs.AdvanceMediaPlayerAbs;

public class VlcPlayer extends AdvanceMediaPlayerAbs {
	
	public void playVlc(String fileName) {
		System.out.println("Playing vlc file. Name: " + fileName);
	}
}