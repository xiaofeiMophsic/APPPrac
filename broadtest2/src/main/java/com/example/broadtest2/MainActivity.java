package com.example.broadtest2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {
    private String mEmail = null;
    private String mPassword = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button = (Button)findViewById(R.id.btn_offline);
        mEmail = getIntent().getStringExtra("email");
        mPassword = getIntent().getStringExtra("password");
        //button.setOnClickListener(v -> {sendBroadcast(new Intent("com.example.broadtest2.receiver.FORCE_OFFLINE"));});
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.broadtest2.receiver.FORCE_OFFLINE");
                intent.putExtra("email", mEmail);
                intent.putExtra("password", mPassword);
                sendBroadcast(intent);
            }
        });
    }

}
