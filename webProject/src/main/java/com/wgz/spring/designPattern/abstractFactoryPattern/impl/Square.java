package com.wgz.spring.designPattern.abstractFactoryPattern.impl;

import com.wgz.spring.designPattern.abstractFactoryPattern.Shape;

public class Square implements Shape {
 
   @Override
   public void draw() {
      System.out.println("Inside Square::draw() method.");
   }
}