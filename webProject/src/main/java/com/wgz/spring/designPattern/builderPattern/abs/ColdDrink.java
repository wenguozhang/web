package com.wgz.spring.designPattern.builderPattern.abs;

import com.wgz.spring.designPattern.builderPattern.Item;
import com.wgz.spring.designPattern.builderPattern.Packing;
import com.wgz.spring.designPattern.builderPattern.impl.Bottle;

public abstract class ColdDrink implements Item {
 
    @Override
    public Packing packing() {
       return new Bottle();
    }
 
    @Override
    public abstract float price();
}