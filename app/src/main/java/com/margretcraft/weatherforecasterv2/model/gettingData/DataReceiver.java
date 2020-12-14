package com.margretcraft.weatherforecasterv2.model.gettingData;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DataReceiver extends BroadcastReceiver {
    public DataReceiver() {
    }

    public void setObserver(PrepearingWeatherData observer) {
        this.observer = observer;
    }

    PrepearingWeatherData observer;

    @Override
    public void onReceive(Context context, Intent intent) {

        observer.update(intent.getParcelableExtra("WeatherRequest"));

    }
}