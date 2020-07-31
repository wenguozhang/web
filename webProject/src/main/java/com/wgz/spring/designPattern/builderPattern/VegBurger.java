package com.wgz.spring.designPattern.builderPattern;

import com.wgz.spring.designPattern.builderPattern.abs.Burger;

public class VegBurger extends Burger {
 
   @Override
   public float price() {
      return 25.0f;
   }
 
   @Override
   public String name() {
      return "Veg Burger";
   }
}