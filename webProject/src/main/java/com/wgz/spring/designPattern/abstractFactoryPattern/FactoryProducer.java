package com.wgz.spring.designPattern.abstractFactoryPattern;

import com.wgz.spring.designPattern.abstractFactoryPattern.abs.AbstractFactory;

public class FactoryProducer {
   public static AbstractFactory getFactory(String choice){
      if(choice.equalsIgnoreCase("SHAPE")){
         return new ShapeFactory();
      } else if(choice.equalsIgnoreCase("COLOR")){
         return new ColorFactory();
      }
      return null;
   }
}