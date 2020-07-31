package com.wgz.spring.designPattern.prototypePattern;

import com.wgz.spring.designPattern.prototypePattern.abs.Shape;

public class Square extends Shape {
 
   public Square(){
     type = "Square";
   }
 
   @Override
   public void draw() {
      System.out.println("Inside Square::draw() method.");
   }
}