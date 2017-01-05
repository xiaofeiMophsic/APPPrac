package com.example.weather.app;

import android.app.Application;
import android.content.Context;

import com.example.weather.utils.volley.VolleyRequest;

/**
 * Created by paozi on 2016/4/5.
 */
public class WeatherApplication extends Application{

    private static WeatherApplication mInstance;

    public WeatherApplication(){
        mInstance = this;
    }

    public static Application getContext() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyRequest.buildRequestQueue(this);
    }
}
