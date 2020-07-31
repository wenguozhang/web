package com.wgz.spring.designPattern.bridgePattern;

import com.wgz.spring.designPattern.bridgePattern.abs.Shape;
import com.wgz.spring.designPattern.bridgePattern.impl.GreenCircle;
import com.wgz.spring.designPattern.bridgePattern.impl.RedCircle;

public class BridgePatternDemo {
   public static void main(String[] args) {
      Shape redCircle = new Circle(100,100, 10, new RedCircle());
      Shape greenCircle = new Circle(100,100, 10, new GreenCircle());
 
      redCircle.draw();
      greenCircle.draw();
      
      new RedCircle().drawCircle(100,100, 10);
      new GreenCircle().drawCircle(100,100, 10);
   }
}