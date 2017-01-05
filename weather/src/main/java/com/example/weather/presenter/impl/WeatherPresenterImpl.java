package com.example.weather.presenter.impl;

import com.example.weather.model.IWeatherModel;
import com.example.weather.model.entity.Weather;
import com.example.weather.model.impl.WeatherModelImpl;
import com.example.weather.presenter.IWeatherPresenter;
import com.example.weather.presenter.OnWeatherListener;
import com.example.weather.ui.view.WeatherView;

/**
 * Created by paozi on 2016/4/5.
 */
public class WeatherPresenterImpl implements IWeatherPresenter, OnWeatherListener{

    private WeatherView mWeatherView;
    private IWeatherModel mWeatherModel;

    public WeatherPresenterImpl(WeatherView mWeatherView) {
        this.mWeatherView = mWeatherView;
        mWeatherModel = new WeatherModelImpl();
    }

    @Override
    public void getWeather(String city) {
        mWeatherView.showLoading();
        mWeatherModel.loadWeather(city, this);
    }

    @Override
    public void onSuccess(Weather weather) {
        mWeatherView.hideLoading();
        mWeatherView.setWeatherInfo(weather);

    }

    @Override
    public void onError() {
        mWeatherView.hideLoading();
        mWeatherView.showError();
    }
}
