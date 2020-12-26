package com.margretcraft.weatherforecasterv2.legacy;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.google.gson.Gson;
import com.margretcraft.weatherforecasterv2.BuildConfig;
import com.margretcraft.weatherforecasterv2.model.jsonmodel.ListRequest;
import com.margretcraft.weatherforecasterv2.model.jsonmodel.Request;
import com.margretcraft.weatherforecasterv2.model.jsonmodel.WeatherRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class GettingDataService_notUse extends JobIntentService {
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?";
    private static final String WEATHER_7_URL = "https://api.openweathermap.org/data/2.5/onecall?";

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        String lat = intent.getStringExtra("lat");
        String lon = intent.getStringExtra("lon");
        String TOWN_COORDINATES = new StringBuilder().append("lat=").append(lat).append("&lon=").append(lon).toString();
        int mod = intent.getIntExtra("mod", 0);

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
                sendBrodcast(weatherRequest);

            } else {
                ListRequest listRequest = gson.fromJson(result, ListRequest.class);
                sendBrodcast(listRequest);
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

    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }

    private void sendBrodcast(Request result) {
        Intent broadcastIntent = new Intent("WeatherRequest");
        broadcastIntent.putExtra("WeatherRequest", result);
        sendBroadcast(broadcastIntent);
    }
}