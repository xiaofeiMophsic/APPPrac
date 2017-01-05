package com.example.weather.model;

import com.example.weather.presenter.OnWeatherListener;

/**
 * Created by paozi on 2016/4/5.
 */
public interface IWeatherModel {

    void loadWeather(String city, OnWeatherListener listener);
}
