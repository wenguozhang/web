package com.wgz.spring.designPattern.adapterPattern.impl;

import com.wgz.spring.designPattern.adapterPattern.AdvancedMediaPlayer;
import com.wgz.spring.designPattern.adapterPattern.MediaPlayer;

public class MediaAdapter implements MediaPlayer {
 
   AdvancedMediaPlayer advancedMusicPlayer;
 
   public MediaAdapter(String audioType){
      if(audioType.equalsIgnoreCase("vlc") ){
         advancedMusicPlayer = new VlcPlayer();       
      } else if (audioType.equalsIgnoreCase("mp4")){
         advancedMusicPlayer = new Mp4Player();
      } else{
    	  System.out.println("Invalid media. "+
    			  audioType + " format not supported");
      }        
   }
 
   @Override
   public void play(String audioType, String fileName) {
      if(audioType.equalsIgnoreCase("vlc")){
         advancedMusicPlayer.playVlc(fileName);
      }else if(audioType.equalsIgnoreCase("mp4")){
         advancedMusicPlayer.playMp4(fileName);
      }
   }
}