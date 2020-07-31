package com.wgz.spring.designPattern.abstractFactoryPattern;

import com.wgz.spring.designPattern.abstractFactoryPattern.abs.AbstractFactory;
import com.wgz.spring.designPattern.abstractFactoryPattern.impl.Circle;
import com.wgz.spring.designPattern.abstractFactoryPattern.impl.Rectangle;
import com.wgz.spring.designPattern.abstractFactoryPattern.impl.Square;

public class ShapeFactory extends AbstractFactory {
    
   @Override
   public Shape getShape(String shapeType){
      if(shapeType == null){
         return null;
      }        
      if(shapeType.equalsIgnoreCase("CIRCLE")){
         return new Circle();
      } else if(shapeType.equalsIgnoreCase("RECTANGLE")){
         return new Rectangle();
      } else if(shapeType.equalsIgnoreCase("SQUARE")){
         return new Square();
      }
      return null;
   }
   
   @Override
   public Color getColor(String color) {
      return null;
   }
}