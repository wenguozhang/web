package com.wgz.spring.designPattern.decoratorPattern.impl;

import com.wgz.spring.designPattern.decoratorPattern.Shape;
import com.wgz.spring.designPattern.decoratorPattern.abs.ShapeDecorator;

public class RedShapeDecorator extends ShapeDecorator {
 
   public RedShapeDecorator(Shape decoratedShape) {
      super(decoratedShape);     
   }
 
   @Override
   public void draw() {
      decoratedShape.draw();         
      setRedBorder(decoratedShape);
   }
 
   private void setRedBorder(Shape decoratedShape){
      System.out.println("Border Color: Red");
   }
}