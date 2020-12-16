package com.margretcraft.weatherforecasterv2.Legacy;


import com.google.gson.Gson;
import com.margretcraft.weatherforecasterv2.BuildConfig;
import com.margretcraft.weatherforecasterv2.model.jsonmodel.ListRequest;
import com.margretcraft.weatherforecasterv2.model.jsonmodel.WeatherRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class GetWeather_notUse extends Observable implements Runnable {
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?";
    private static final String WEATHER_7_URL = "https://api.openweathermap.org/data/2.5/onecall?";

    private final String TOWN_COORDINATES;
    private final Observer observer;

    private final String lat;
    private final String lon;
    private final int mod;

    public GetWeather_notUse(Observer observer, int mod, String lat, String lon) {

        this.observer = observer;
        this.lat = lat;
        this.lon = lon;
        this.TOWN_COORDINATES = new StringBuilder().append("lat=").append(lat).append("&lon=").append(lon).toString();
        this.mod = mod;
    }

    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }

    @Override
    public void run() {
        StringBuilder sb;
        if (mod == 0) {
            sb = new StringBuilder(WEATHER_URL);
            sb.append(TOWN_COORDINATES).append("&lang=").append(Locale.getDefault().getLanguage()).append("&appid=").append(BuildConfig.WEATHER_API_KEY);
        } else {
            sb = new StringBuilder(WEATHER_7_URL);
            sb.append(TOWN_COORDINATES).append("&lang=").append(Locale.getDefault().getLanguage()).append("&exclude=").append("minutely,hourly").append("&appid=").append(BuildConfig.WEATHER_API_KEY);
        }

        HttpsURLConnection urlConnection = null;
        try {

            URL uri = new URL(sb.toString());
            urlConnection = (HttpsURLConnection) uri.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String result = getLines(in);

            Gson gson = new Gson();
            if (mod == 0) {
                WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
                observer.update(this, weatherRequest);
            } else {
                ListRequest listRequest = gson.fromJson(result, ListRequest.class);
                observer.update(this, listRequest);
            }
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != urlConnection) {
                urlConnection.disconnect();
            }
        }
    }
}
