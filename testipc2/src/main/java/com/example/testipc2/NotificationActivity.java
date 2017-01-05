package com.example.testipc2;

import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.ipcservice.utils.Constants;

import org.w3c.dom.Text;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Intent intent = getIntent();
        String packageName = intent.getStringExtra(Constants.PACKAGE_NAME);

        Log.e("test1_main", "onCreate: "+ packageName);

        TextView textView = (TextView) findViewById(R.id.id_notify_tx);
        textView.setText("hello" + packageName);
    }
}
