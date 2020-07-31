package com.wgz.spring.designPattern.abstractFactoryPattern.impl;

import com.wgz.spring.designPattern.abstractFactoryPattern.Color;

public class Blue implements Color {
 
   @Override
   public void fill() {
      System.out.println("Inside Blue::fill() method.");
   }
}