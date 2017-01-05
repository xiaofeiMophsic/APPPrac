package com.example.ipcservice.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

import com.example.ipcservice.AIDLServer;
import com.example.ipcservice.receiver.PushReceiver;
import com.example.ipcservice.receiver.ServiceReceiver;
import com.example.ipcservice.utils.Constants;

public class MyService extends Service {

    public static final String SERVICE_IN_PROCESS_NAME = "rytong_push_service";
    private static final String TAG = "myservice";
    private MyBinder mMyBinder = new MyBinder();
    private SharedPreferences sdpf;

    private String packageName;

    private BroadcastReceiver mReceiver;

    public MyService() {
        mReceiver = new ServiceReceiver();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        sdpf = getSharedPreferences("sp", MODE_PRIVATE);
        Log.i(TAG, "onCreate: myservice oncreate");
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        String arg = intent.getStringExtra("appname");
        Log.i("service", "来自service" + arg);
        Log.i(TAG, "sharedpreference:" + sdpf.getString("string1", "null"));

        this.packageName = intent.getStringExtra(Constants.PACKAGE_NAME);
        Log.i(TAG, "onStartCommand: ");

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PushReceiver.RECEIVER_ACTION + "." + packageName);
        registerReceiver(mReceiver, intentFilter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (i % 2 == 0){
                        packageName = "com.example.testipc2";
                    } else {
                        packageName = "com.example.testipc1";
                    }

                    Intent intent1 = new Intent(PushReceiver.RECEIVER_ACTION + "." + packageName);

                    intent1.putExtra(Constants.PACKAGE_NAME, packageName);
                    sendBroadcast(intent1);
                }

            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        Log.i(TAG, "onDestroy: myservice ondestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {

        return mMyBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public class MyBinder extends AIDLServer.Stub {

        @Override
        public String basicTypes(String src) {
            Log.i(TAG, "basicTypes: bastType." + src);
            return src.toUpperCase() + Process.myPid();
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String[] packages = MyService.this.getPackageManager().getPackagesForUid(getCallingUid());
            String packageName;
            if (packages != null && packages.length > 0) {
                Log.i(TAG, "onTransact: " + packages[0]);
            }
            return super.onTransact(code, data, reply, flags);
        }
    }
}
