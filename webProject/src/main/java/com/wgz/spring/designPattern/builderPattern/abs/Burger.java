package com.wgz.spring.designPattern.builderPattern.abs;

import com.wgz.spring.designPattern.builderPattern.Item;
import com.wgz.spring.designPattern.builderPattern.Packing;
import com.wgz.spring.designPattern.builderPattern.impl.Wrapper;

public abstract class Burger implements Item {
 
   @Override
   public Packing packing() {
      return new Wrapper();
   }
 
   @Override
   public abstract float price();
}