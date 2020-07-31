package com.wgz.spring.designPattern.abstractFactoryPattern.impl;

import com.wgz.spring.designPattern.abstractFactoryPattern.Color;

public class Red implements Color {
 
   @Override
   public void fill() {
      System.out.println("Inside Red::fill() method.");
   }
}