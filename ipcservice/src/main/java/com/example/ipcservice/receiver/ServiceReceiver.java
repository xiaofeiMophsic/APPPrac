package com.example.ipcservice.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.ipcservice.utils.Constants;

/**
 * 作者：xiaofei
 * 日期：2016/4/28
 * 邮箱：paozi.xiaofei.123@gmail.com
 */
public class ServiceReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        String packageName = intent.getStringExtra(Constants.PACKAGE_NAME);

        context.sendBroadcast(PushReceiver.getIntent(packageName, "转发包"));
    }
}
