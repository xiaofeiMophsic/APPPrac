package com.example.testipc2;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ipcservice.AIDLServer;
import com.example.ipcservice.utils.ActivityManagerUtils;
import com.example.ipcservice.utils.Constants;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TEST2 MainACtivity";
    Button btnBindService, btnUnbindService, btnGetMessage;
    private AIDLServer myBinder;
    private String packageName;
    private SharedPreferences sdpf;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = AIDLServer.Stub.asInterface(service);
            btnGetMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String result = "";
                    if (myBinder != null) {
                        try {
                            result = myBinder.basicTypes("testas");
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(MainActivity.this, "转换结果为:" + result, Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "binder为初始化", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sdpf = getSharedPreferences("sp", MODE_PRIVATE);
        SharedPreferences.Editor edit = sdpf.edit();
        edit.putString("string1", "test1");
        edit.commit();


        btnBindService = (Button) findViewById(R.id.btn_bind_service);
        btnGetMessage = (Button) findViewById(R.id.get_message);

        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        packageName = ActivityManagerUtils.getPackageNameByRunningProcess(activityManager);
        if(null == packageName){
            packageName = getPackageName();
        }

        btnBindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.ipcservice.AIDLServer");
                intent.setPackage(packageName);
                intent.putExtra(Constants.PACKAGE_NAME, getPackageName());
                intent.putExtra("appname", "testipc2");
                //bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
                startService(intent);

//                List<ResolveInfo> infos = getApplicationContext().getPackageManager().queryIntentServices(new Intent("com.example.ipcservice.AIDLServer"), 0);
//                for (ResolveInfo info : infos){
//                    Log.w(TAG, "onClick: " + info.serviceInfo.packageName);
//                }
            }
        });
    }
}
