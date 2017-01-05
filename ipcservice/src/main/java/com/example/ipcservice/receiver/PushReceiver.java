package com.example.ipcservice.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import com.example.ipcservice.R;
import com.example.ipcservice.utils.Constants;

import java.util.List;
import java.util.Random;

/**
 * 作者：xiaofei
 * 日期：2016/4/26
 * 邮箱：paozi.xiaofei.123@gmail.com
 */
public class PushReceiver extends BroadcastReceiver {

    public static final String RECEIVER_ACTION = "com.example.pushreceiver";
    public static final String APP_ARG = "app_arg";

    public static Intent getIntent(String packageName, String arg1) {
        Intent intent = new Intent(RECEIVER_ACTION);
        intent.setPackage(packageName);
        intent.putExtra(Constants.PACKAGE_NAME, packageName);
        intent.putExtra(APP_ARG, arg1);
        return intent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String packageName = intent.getStringExtra(Constants.PACKAGE_NAME);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent1 = new Intent();
        intent1.setComponent(new ComponentName(packageName, getClass(context)));
        intent1.putExtra(Constants.PACKAGE_NAME, packageName);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Random random = new Random();

        PendingIntent contentIntent = PendingIntent.getActivity(context, random.nextInt(),
                intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notify2 = new Notification.Builder(context)
                .setSmallIcon(android.R.drawable.sym_call_incoming) // 设置状态栏中的小图片，尺寸一般建议在24×24，这个图片同样也是在下拉状态栏中所显示，如果在那里需要更换更大的图片，可以使用setLargeIcon(Bitmap
                .setTicker("TickerText:" + packageName + "您有新短消息，请注意查收！")// 设置在status
                // bar上显示的提示文字
                .setContentTitle(packageName)// 设置在下拉status
                // bar后Activity，本例子中的NotififyMessage的TextView中显示的标题
                .setContentText("This is the notification message")// TextView中显示的详细内容
                .setContentIntent(contentIntent) // 关联PendingIntent
                .build(); // 需要注意build()是在API level
        notify2.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(random.nextInt(), notify2);
    }

    private String getClass(Context context) {
        String className = "";

        if (null == context) return className;

        Intent intent = new Intent("com.rytong.push.test");
        intent.setPackage(context.getPackageName());

        List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(intent, 0);

        ResolveInfo info = resolveInfoList.iterator().next();

        if (null != info) className = info.activityInfo.name;
        return className;
    }
}
