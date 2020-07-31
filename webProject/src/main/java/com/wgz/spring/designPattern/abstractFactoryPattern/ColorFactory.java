package com.wgz.spring.designPattern.abstractFactoryPattern;

import com.wgz.spring.designPattern.abstractFactoryPattern.abs.AbstractFactory;
import com.wgz.spring.designPattern.abstractFactoryPattern.impl.Blue;
import com.wgz.spring.designPattern.abstractFactoryPattern.impl.Green;
import com.wgz.spring.designPattern.abstractFactoryPattern.impl.Red;

public class ColorFactory extends AbstractFactory {
    
   @Override
   public Shape getShape(String shapeType){
      return null;
   }
   
   @Override
   public Color getColor(String color) {
      if(color == null){
         return null;
      }        
      if(color.equalsIgnoreCase("RED")){
         return new Red();
      } else if(color.equalsIgnoreCase("GREEN")){
         return new Green();
      } else if(color.equalsIgnoreCase("BLUE")){
         return new Blue();
      }
      return null;
   }
}