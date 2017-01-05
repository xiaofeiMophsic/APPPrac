package com.example.paozi.appprac;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

/**
 * 作者：xiaofei
 * 日期：2016/12/19
 * 邮箱：paozi.xiaofei.123@gmail.com
 */

public class TestHandlerThread {

    TestHandlerThread() {


        HandlerThread ht = new HandlerThread("my-thread");
        ht.start();

        Handler h = new Handler(ht.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {

                }
            }
        };

        Message m = Message.obtain();
        m.what = 0x123;
        h.sendMessage(m);
    }
}
