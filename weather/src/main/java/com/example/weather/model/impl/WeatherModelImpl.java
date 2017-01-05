package com.example.weather.model.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.weather.model.IWeatherModel;
import com.example.weather.model.entity.Weather;
import com.example.weather.presenter.OnWeatherListener;
import com.example.weather.utils.volley.VolleyRequest;

/**
 * Created by paozi on 2016/4/5.
 */
public class WeatherModelImpl implements IWeatherModel {
    @Override
    public void loadWeather(String city, final OnWeatherListener listener) {
        VolleyRequest.newInstance().newGsonRequest("http://www.tngou.net/api/top/show?id=" + city, Weather.class,
                new Response.Listener<Weather>() {
                    @Override
                    public void onResponse(Weather response) {
                        if (response != null) {
                            listener.onSuccess(response);
                        }else
                            listener.onError();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError();
                    }
                });
    }
}
