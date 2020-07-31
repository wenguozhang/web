package com.wgz.spring.designPattern.decoratorPattern.impl;

import com.wgz.spring.designPattern.decoratorPattern.Shape;

public class Circle implements Shape {
 
   @Override
   public void draw() {
      System.out.println("Shape: Circle");
   }
}