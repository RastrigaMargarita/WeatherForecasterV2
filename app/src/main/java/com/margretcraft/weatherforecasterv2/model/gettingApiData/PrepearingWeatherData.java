package com.margretcraft.weatherforecasterv2.model.gettingApiData;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.margretcraft.weatherforecasterv2.App;
import com.margretcraft.weatherforecasterv2.BuildConfig;
import com.margretcraft.weatherforecasterv2.R;
import com.margretcraft.weatherforecasterv2.model.ForecastAdapter;
import com.margretcraft.weatherforecasterv2.model.TownClass;
import com.margretcraft.weatherforecasterv2.model.jsonmodel.ListRequest;
import com.margretcraft.weatherforecasterv2.model.jsonmodel.WeatherRequest;

import java.io.IOException;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import retrofit2.Response;

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
    private float tempK;
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
        if (mod == 0) {
            //Используем execute так как мы уже в параллельном потоке
            try {
                Response<WeatherRequest> response = App.getOpenWeatherApi().loadWeather(points[1].substring(0, 5), points[0].substring(0, 5), Locale.getDefault().getLanguage(), BuildConfig.WEATHER_API_KEY).execute();
                if (response.body() != null) {
                    update(response.body());
                }
            } catch (IOException e) {
                e.printStackTrace();

            }

        } else {
            Response<ListRequest> response = null;
            try {
                response = App.getOpenWeatherApi().loadForecast(points[1].substring(0, 5), points[0].substring(0, 5), Locale.getDefault().getLanguage(), "minutely,hourly", BuildConfig.WEATHER_API_KEY).execute();
                if (response.body() != null) {
                    update(response.body());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            update(response.body());
        }

    }

    public float getTempK() {
        return tempK;
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
            tempK = wr.getMain().getTemp();
            StringBuilder sb = new StringBuilder();
            if (tempmes) {
                sb.append("%d").append(context.getString(R.string.tempmes1)).append("...%d").append(context.getString(R.string.tempmes1));
                minMax = String.format(sb.toString(), (int) ((wr.getMain().getTemp_min()) - context.getResources().getInteger(R.integer.transferT)), (int) ((wr.getMain().getTemp_max()) - context.getResources().getInteger(R.integer.transferT)));
                temp = "" + (int) (tempK - context.getResources().getInteger(R.integer.transferT)) + context.getString(R.string.tempmes1);
            } else {
                sb.append("%.2f").append(context.getString(R.string.tempmes2)).append("...%.2f").append(context.getString(R.string.tempmes2));
                minMax = String.format(sb.toString(), (wr.getMain().getTemp_min()), (wr.getMain().getTemp_max()));
                temp = "" + tempK + context.getString(R.string.tempmes2);
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
            forecastAdapter = new ForecastAdapter(context, tempmes, days, lr.getDaily());
        }
        observer.update(this, this);
    }
}

