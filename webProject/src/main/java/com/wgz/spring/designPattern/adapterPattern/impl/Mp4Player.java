package com.wgz.spring.designPattern.adapterPattern.impl;

import com.wgz.spring.designPattern.adapterPattern.abs.AdvanceMediaPlayerAbs;

public class Mp4Player extends AdvanceMediaPlayerAbs{
 
   public void playMp4(String fileName) {
      System.out.println("Playing mp4 file. Name: "+ fileName);      
   }
}