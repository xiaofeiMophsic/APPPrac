package com.example.weather.ui.view;

import com.example.weather.model.entity.Weather;

/**
 * Created by paozi on 2016/4/5.
 */
public interface WeatherView {

    void showLoading();
    void hideLoading();
    void showError();
    void setWeatherInfo(Weather weather);
}
