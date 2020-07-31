package com.wgz.spring.designPattern.builderPattern;

import com.wgz.spring.designPattern.builderPattern.abs.Burger;

public class ChickenBurger extends Burger {
 
   @Override
   public float price() {
      return 50.5f;
   }
 
   @Override
   public String name() {
      return "Chicken Burger";
   }
}