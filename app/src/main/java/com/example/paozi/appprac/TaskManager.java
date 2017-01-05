package com.example.paozi.appprac;

import android.os.Looper;


/**
 * 作者：xiaofei
 * 日期：2016/10/16
 * 邮箱：paozi.xiaofei.123@gmail.com
 */

public class TaskManager extends Thread{


    @Override
    public void run() {
        Looper.prepare();



        Looper.loop();
    }
}
