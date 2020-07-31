package com.wgz.spring.designPattern.builderPattern;

import com.wgz.spring.designPattern.builderPattern.abs.ColdDrink;

public class Pepsi extends ColdDrink {
 
   @Override
   public float price() {
      return 35.0f;
   }
 
   @Override
   public String name() {
      return "Pepsi";
   }
}