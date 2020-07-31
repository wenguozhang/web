package com.wgz.spring.designPattern.adapterPattern.abs;

import com.wgz.spring.designPattern.adapterPattern.AdvancedMediaPlayer;

public abstract class AdvanceMediaPlayerAbs implements AdvancedMediaPlayer{
	
	@Override
	public void playVlc(String fileName) {
	//什么也不做
	}
	
	@Override
    public void playMp4(String fileName) {
		//什么也不做
	}
} 
