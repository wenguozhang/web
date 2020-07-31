package com.wgz.spring.designPattern.decoratorPattern.impl;

import com.wgz.spring.designPattern.decoratorPattern.Shape;

public class Rectangle implements Shape {
 
   @Override
   public void draw() {
      System.out.println("Shape: Rectangle");
   }
}