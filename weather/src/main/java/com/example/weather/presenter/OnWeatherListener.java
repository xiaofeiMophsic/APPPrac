package com.example.weather.presenter;

import com.example.weather.model.entity.Weather;

/**
 * Created by paozi on 2016/4/5.
 */
public interface OnWeatherListener {

    void onSuccess(Weather weather);
    void onError();
}
