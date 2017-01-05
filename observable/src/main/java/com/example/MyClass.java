package com.example;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class MyClass extends Observable{

    private String sun ;
    private boolean isSunRise;
    private Timer mTimer = new Timer();

    public void sunRise(){
        sun = "sun rise...";
        isSunRise = true;
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(isSunRise) {
                    sun = "after five hours";
                    setChanged();
                    notifyObservers();
                }
            }
        }, 2000, 5000);
    }

    public void sunDown() {
        mTimer.cancel();
        sun = "sun down, go to bed! Z ZZ";
        //setChanged();
        notifyObservers();
        isSunRise = false;
    }

    public String getSun() {
        return sun;
    }


}
