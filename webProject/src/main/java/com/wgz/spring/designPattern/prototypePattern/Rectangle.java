package com.wgz.spring.designPattern.prototypePattern;

import com.wgz.spring.designPattern.prototypePattern.abs.Shape;

public class Rectangle extends Shape {
 
   public Rectangle(){
     type = "Rectangle";
   }
 
   @Override
   public void draw() {
      System.out.println("Inside Rectangle::draw() method.");
   }
}