package com.example.paozi.appprac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new TaskManager().start();
    }
}
