package com.margretcraft.weatherforecasterv2.Legacy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.margretcraft.weatherforecasterv2.model.gettingData.PrepearingWeatherData;

public class DataReceiver_notUse extends BroadcastReceiver {
    public DataReceiver_notUse() {
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