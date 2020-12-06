package com.margretcraft.weatherforecasterv2.ui.weatherUI;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.margretcraft.weatherforecasterv2.MainNdActivity;
import com.margretcraft.weatherforecasterv2.model.GetWeather;
import com.margretcraft.weatherforecasterv2.model.jsonmodel.Request;
import com.margretcraft.weatherforecasterv2.model.jsonmodel.WeatherRequest;

import java.util.Observable;
import java.util.Observer;

public class WeatherViewModel extends ViewModel implements Observer {

    private Activity activity;
    private final MutableLiveData<Request> weatherRequestMLD = new MutableLiveData<>();

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public MutableLiveData<Request> getWeatherRequestMLD() {
        return weatherRequestMLD;
    }

    public void startGettingWeatherData() {

        String[] points = ((MainNdActivity) activity).getCurrentTown().getPoint().split(",");
        GetWeather getWeather = new GetWeather(this, 0, points[1].substring(0, 5), points[0].substring(0, 5));

        (new Thread(getWeather)).start();
    }
    public void startGettingForecastData() {

        String[] points = ((MainNdActivity) activity).getCurrentTown().getPoint().split(",");
        GetWeather getWeather = new GetWeather(this, 1, points[1].substring(0, 5), points[0].substring(0, 5));

        (new Thread(getWeather)).start();
    }
    @Override
    public void update(Observable o, final Object arg) {
        activity.runOnUiThread(() -> weatherRequestMLD.setValue((Request) arg));
    }

}