package com.margretcraft.weatherforecasterv2.model.gettingData;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.margretcraft.weatherforecasterv2.R;
import com.margretcraft.weatherforecasterv2.model.ForecastAdapter;
import com.margretcraft.weatherforecasterv2.model.TownClass;
import com.margretcraft.weatherforecasterv2.model.jsonmodel.ListRequest;
import com.margretcraft.weatherforecasterv2.model.jsonmodel.WeatherRequest;

import java.util.Observable;
import java.util.Observer;

import static androidx.core.app.JobIntentService.enqueueWork;

public class PrepearingWeatherData extends Observable implements Runnable {
    private final TownClass currentTown;
    private final boolean tempmes;
    private final boolean windmes;
    private final int mod;
    private final String[] days;

    private final Observer observer;

    private String minMax;
    private String state;
    private String temp;
    private String wind;
    private Integer windImage;
    private ForecastAdapter forecastAdapter;

    private Context context;

    public PrepearingWeatherData(Observer observer, int mod, Context context, TownClass currentTown,
                                 boolean tempmes, boolean windmes, String[] days) {
        this.observer = observer;
        this.context = context;
        this.currentTown = currentTown;
        this.tempmes = tempmes;
        this.windmes = windmes;
        this.mod = mod;
        this.days = days;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run() {

        String[] points = currentTown.getPoint().split(",");

        Intent intentForService = new Intent(context, GettingDataService.class);
        intentForService.putExtra("mod", mod);
        intentForService.putExtra("lat", points[1].substring(0, 5));
        intentForService.putExtra("lon", points[0].substring(0, 5));
        DataReceiver dr = new DataReceiver();

        dr.setObserver(this);
        IntentFilter filter = new IntentFilter("WeatherRequest");
        context.registerReceiver(dr, filter);
        enqueueWork(context, GettingDataService.class, 225, intentForService);
    }

    public String getMinMax() {
        return minMax;
    }

    public String getState() {
        return state;
    }

    public String getTemp() {
        return temp;
    }

    public String getWind() {
        return wind;
    }

    public Integer getWindImage() {
        return windImage;
    }

    public ForecastAdapter getForecastAdapter() {
        return forecastAdapter;
    }

    public void update(Object arg) {
        if (arg instanceof WeatherRequest) {
            WeatherRequest wr = (WeatherRequest) arg;
                StringBuilder sb = new StringBuilder();
                if (tempmes) {
                    sb.append("%d").append(context.getString(R.string.tempmes1)).append("...%d").append(context.getString(R.string.tempmes1));
                    minMax = String.format(sb.toString(), (int) ((wr.getMain().getTemp_min()) - context.getResources().getInteger(R.integer.transferT)), (int) ((wr.getMain().getTemp_max()) - context.getResources().getInteger(R.integer.transferT)));
                    temp = "" + (int) (wr.getMain().getTemp() - context.getResources().getInteger(R.integer.transferT)) + context.getString(R.string.tempmes1);
                } else {
                    sb.append("%.2f").append(context.getString(R.string.tempmes2)).append("...%.2f").append(context.getString(R.string.tempmes2));
                    minMax = String.format(sb.toString(), (wr.getMain().getTemp_min()), (wr.getMain().getTemp_max()));
                    temp = "" + wr.getMain().getTemp() + context.getString(R.string.tempmes2);
                }

                sb.delete(0, sb.length());
                if (windmes) {
                    wind = String.format(sb.append("%.1f ").append(context.getString(R.string.windmes1)).toString(), wr.getWind().getSpeed());
                } else {
                    wind = String.format(sb.append("%.1f").append(context.getString(R.string.windmes2)).toString(), wr.getWind().getSpeed() * 0.10 * context.getResources().getInteger(R.integer.transferW));
                }
                sb.delete(0, sb.length());

                windImage = context.getResources().getIdentifier("wd" + (Math.round(wr.getWind().getDeg() * 1.0 / 45) + 1), "drawable", context.getPackageName());
                state = wr.getWeather()[0].getMain() + "\n" + wr.getWeather()[0].getDescription();


        } else if (arg instanceof ListRequest) {
            ListRequest lr = (ListRequest) arg;
                String mes = (tempmes ? context.getString(R.string.tempmes1) : context.getString(R.string.tempmes2));
                forecastAdapter = new ForecastAdapter(context, mes, days, lr.getDaily());
        }
        observer.update(this, this);
    }
}

