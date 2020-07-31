package com.wgz.spring.designPattern.builderPattern.impl;

import com.wgz.spring.designPattern.builderPattern.Packing;

public class Bottle implements Packing {
 
   @Override
   public String pack() {
      return "Bottle";
   }
}