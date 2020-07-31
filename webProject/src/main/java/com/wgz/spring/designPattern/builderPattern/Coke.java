package com.wgz.spring.designPattern.builderPattern;

import com.wgz.spring.designPattern.builderPattern.abs.ColdDrink;

public class Coke extends ColdDrink {
 
   @Override
   public float price() {
      return 30.0f;
   }
 
   @Override
   public String name() {
      return "Coke";
   }
}