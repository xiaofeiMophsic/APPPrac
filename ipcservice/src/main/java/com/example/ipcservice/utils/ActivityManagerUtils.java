package com.example.ipcservice.utils;

import android.app.ActivityManager;
import android.app.Service;
import android.util.Log;

import com.example.ipcservice.service.MyService;

import java.util.List;

/**
 * 作者：xiaofei
 * 日期：2016/4/22
 * 邮箱：paozi.xiaofei.123@gmail.com
 */
public final class ActivityManagerUtils {

    public static String getPackageNameByRunningProcess(ActivityManager activityManager) {

        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo info : serviceList) {
            System.out.println(info.service.getClassName());
            if (info.process.contains(MyService.SERVICE_IN_PROCESS_NAME)) {//如果当前正在运行的进程中有包含"remoteforrytong",则获取其进程名，默认包名
                String targetServiceClassName = info.service.getClassName();
                if (MyService.class.getCanonicalName().equals(targetServiceClassName)) {
                    return info.service.getPackageName();
                }
            }
        }
        return null;
    }
}
