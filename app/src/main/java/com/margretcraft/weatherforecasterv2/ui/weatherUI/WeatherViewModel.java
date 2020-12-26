package com.margretcraft.weatherforecasterv2.ui.weatherUI;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.margretcraft.weatherforecasterv2.MainNdActivity;
import com.margretcraft.weatherforecasterv2.model.ForecastAdapter;
import com.margretcraft.weatherforecasterv2.model.gettingApiData.PrepearingWeatherData;

import java.util.Observable;
import java.util.Observer;

public class WeatherViewModel extends ViewModel implements Observer {

    private MainNdActivity activity;
    private final MutableLiveData<String> minMaxMLD = new MutableLiveData<>();
    private final MutableLiveData<String> stateMLD = new MutableLiveData<>();
    private final MutableLiveData<String> tempMLD = new MutableLiveData<>();
    private final MutableLiveData<String> windMLD = new MutableLiveData<>();
    private final MutableLiveData<Integer> windImageMLD = new MutableLiveData<>();
    private final MutableLiveData<ForecastAdapter> forecastAdapterMLD = new MutableLiveData<>();

    public void setActivity(Activity activity) {
        this.activity = (MainNdActivity) activity;
    }

    public MutableLiveData<String> getMinMaxMLD() {
        return minMaxMLD;
    }

    public MutableLiveData<String> getStateMLD() {
        return stateMLD;
    }

    public MutableLiveData<String> getTempMLD() {
        return tempMLD;
    }

    public MutableLiveData<String> getWindMLD() {
        return windMLD;
    }

    public MutableLiveData<Integer> getWindImageMLD() {
        return windImageMLD;
    }

    public MutableLiveData<ForecastAdapter> getForecastAdapterMLD() {
        return forecastAdapterMLD;
    }

    public void startGettingData(int mod) {

        new Thread(new PrepearingWeatherData(this, mod, activity.getApplicationContext(),
                activity.getCurrentTown(), activity.isTempmes(), activity.isWindmes(),
                activity.getDays())).start();
    }

    @Override
    public void update(Observable o, final Object arg) {

        final PrepearingWeatherData pwd = (PrepearingWeatherData) arg;
        activity.runOnUiThread(() -> {
            if (pwd.getMinMax() != null) {
                minMaxMLD.setValue(pwd.getMinMax());
            }
            if (pwd.getState() != null) {
                stateMLD.setValue(pwd.getState());
            }
            if (pwd.getTemp() != null) {
                tempMLD.setValue(pwd.getTemp());
            }
            if (pwd.getWind() != null) {
                windMLD.setValue(pwd.getWind());
            }
            if (pwd.getWindImage() != null) {
                windImageMLD.setValue(pwd.getWindImage());
            }
            if (pwd.getForecastAdapter() != null) {
                forecastAdapterMLD.setValue(pwd.getForecastAdapter());
            }

        });
        if (pwd.getTempK() != 0) {
            activity.writeHistory(pwd.getTempK());
        }
    }

}
