package com.wgz.spring.designPattern.prototypePattern;

import com.wgz.spring.designPattern.prototypePattern.abs.Shape;

public class Circle extends Shape {
 
   public Circle(){
     type = "Circle";
   }
 
   @Override
   public void draw() {
      System.out.println("Inside Circle::draw() method.");
   }
}