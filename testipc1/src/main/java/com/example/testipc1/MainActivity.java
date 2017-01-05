package com.example.testipc1;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ipcservice.AIDLServer;
import com.example.ipcservice.utils.ActivityManagerUtils;
import com.example.ipcservice.utils.Constants;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private String packageName;
    private AIDLServer mServer;
    private Button btnBindService, btnUnbindService, btnGetMessage;
    private ActivityManager mActivityManager;
    private SharedPreferences sdpf;
    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected: service connected");
            mServer = AIDLServer.Stub.asInterface(service);
            btnGetMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String processedStr = "";
                    try {
                        processedStr = mServer.basicTypes("helloworld");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(MainActivity.this, "onServiceConnected: 处理过后的字符串" + processedStr, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected: service disconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sdpf = getSharedPreferences("sp", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sdpf.edit();
//        editor.putString("string1", "来自ipc1");
//        editor.commit();

        btnBindService = (Button) findViewById(R.id.btn_bind_service);
        btnUnbindService = (Button) findViewById(R.id.btn_unbind_service);
        btnGetMessage = (Button) findViewById(R.id.get_message);

        btnBindService.setOnClickListener(this);
        btnUnbindService.setOnClickListener(this);
        btnGetMessage.setOnClickListener(this);

        mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        packageName = ActivityManagerUtils.getPackageNameByRunningProcess(mActivityManager);

        if (null == packageName){
            packageName = getPackageName();
        }


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_bind_service:
                Log.i(TAG, "onClick: 绑定服务");
                Intent intent3 = new Intent("com.example.ipcservice.AIDLServer");
                intent3.setPackage(packageName);
                intent3.putExtra(Constants.PACKAGE_NAME, getPackageName());
                intent3.putExtra("appname", "test1");
                startService(intent3);
//                bindService(intent3, mServiceConnection, BIND_AUTO_CREATE);
//                final TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//                final String tmDevice, tmSerial, androidId;
//                tmDevice = "123456789102135";
//                tmSerial = "12354689897999979";
//                androidId = ""
//                        + android.provider.Settings.Secure.getString(
//                        getContentResolver(),
//                        android.provider.Settings.Secure.ANDROID_ID);
//                UUID deviceUuid = new UUID(androidId.hashCode(),
//                        ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
//                Log.i(TAG, "onCreate: " + deviceUuid.toString());
                break;
            case R.id.btn_unbind_service:
                Log.i(TAG, "onClick: 解绑服务");
                unbindService(mServiceConnection);
                break;
            default:
                break;
        }
    }
}
