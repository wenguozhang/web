package com.wgz.spring.designPattern.facadePattern;

import com.wgz.spring.designPattern.abstractFactoryPattern.Shape;
import com.wgz.spring.designPattern.abstractFactoryPattern.impl.Circle;
import com.wgz.spring.designPattern.abstractFactoryPattern.impl.Rectangle;
import com.wgz.spring.designPattern.abstractFactoryPattern.impl.Square;

public class ShapeMaker {
   private Shape circle;
   private Shape rectangle;
   private Shape square;
 
   public ShapeMaker() {
      circle = new Circle();
      rectangle = new Rectangle();
      square = new Square();
   }
 
   public void drawCircle(){
      circle.draw();
   }
   public void drawRectangle(){
      rectangle.draw();
   }
   public void drawSquare(){
      square.draw();
   }
}