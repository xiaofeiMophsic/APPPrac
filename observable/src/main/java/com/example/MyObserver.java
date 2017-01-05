package com.example;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by paozi on 2016/4/13.
 */
public class MyObserver implements Observer{

    @Override
    public void update(Observable o, Object arg) {

        System.out.println(((MyClass)o).getSun());
    }
}
