package com.margretcraft.weatherforecasterv2;

import android.app.Application;
import android.content.Context;

import com.margretcraft.weatherforecasterv2.model.gettingData.ApiHolder;
import com.margretcraft.weatherforecasterv2.model.gettingData.OpenWeather;

public class App extends Application {
    private static App INSTANCE;

    private static ApiHolder apiHolder;

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;
        apiHolder = new ApiHolder();
    }

    public static OpenWeather getOpenWeatherApi() {
        return apiHolder.getOpenWeather();
    }

    public Context getAppContext() {
        return INSTANCE;
    }
}
