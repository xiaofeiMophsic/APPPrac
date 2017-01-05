package com.example.popupwindow;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * 作者：xiaofei
 * 日期：2016/8/17
 * 邮箱：paozi.xiaofei.123@gmail.com
 */
public class Notifier {
    private NotificationManager notificationManager = null;
    private Notification notification = null;
    private RemoteViews contentView = null;

    private Context context;

    NotificationCompat.Builder mBuilder;
    public Notifier(Context context) {
        this.context = context;
        initNotify();
        notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /** 初始化通知栏 */
    private void initNotify(){
        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContentTitle("测试标题")
                .setContentText("测试内容")
//                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
//				.setNumber(number)//显示数量
                .setTicker("测试通知来啦")//通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
                .setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
				.setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_SOUND)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.mipmap.ic_launcher);
    }

    public void createRecordingNotification() {//创建通知
        notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notification = new Notification();
        notification.flags = Notification.FLAG_ONGOING_EVENT;

        contentView = new RemoteViews(context.getPackageName(),
                R.layout.notification);//初始化自定义的通知样式
        contentView.setImageViewResource(R.id.large_img, R.drawable.large_image);


        notification.contentView = contentView;//设置通知样式为自定义的样式

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent contentItent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        notification.contentIntent = contentItent;
        notificationManager.notify(0, notification);
    }

    public void createBigBoxNotification(){
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        String[] events = {"第一条", "第二条", "第三条", "第四条", "第五条"};
        // Sets a title for the Inbox style big view
        inboxStyle.setBigContentTitle("大视图内容:");
        // Moves events into the big view
        for (int i=0; i < events.length; i++) {
            inboxStyle.addLine(events[i]);
        }
        mBuilder.setContentTitle("测试标题")
                .setContentText("测试内容")
                .setStyle(inboxStyle)//设置风格
                .setTicker("测试通知来啦");// 通知首次出现在通知栏，带上升动画效果的
        notificationManager.notify(1001, mBuilder.build());
    }

    public void createBigPictureStyleNotification() {
        Notification notif = new Notification.Builder(context)
                //.setContentTitle("New photo from my photos")
                //.setContentText("这里本应该显示一堆图片的")
                .setSmallIcon(R.mipmap.ic_launcher)
                //.setLargeIcon(R.drawable.large_image)
                .setStyle(new Notification.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.capture)))
                .build();
        notificationManager.notify(1001, notif);
    }
}
