package com.wgz.spring.designPattern.builderPattern.impl;

import com.wgz.spring.designPattern.builderPattern.Packing;

public class Wrapper implements Packing {
 
   @Override
   public String pack() {
      return "Wrapper";
   }
}