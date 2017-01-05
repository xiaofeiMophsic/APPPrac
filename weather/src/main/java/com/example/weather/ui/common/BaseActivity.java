package com.example.weather.ui.common;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by paozi on 2016/4/5.
 */
public class BaseActivity extends AppCompatActivity {

    protected  <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }
}
