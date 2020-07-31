package com.wgz.spring.designPattern.abstractFactoryPattern.impl;

import com.wgz.spring.designPattern.abstractFactoryPattern.Shape;

public class Circle implements Shape {
 
   @Override
   public void draw() {
      System.out.println("Inside Circle::draw() method.");
   }
}