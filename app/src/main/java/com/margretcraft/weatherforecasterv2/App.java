package com.margretcraft.weatherforecasterv2;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.margretcraft.weatherforecasterv2.dao.HistoryDAO;
import com.margretcraft.weatherforecasterv2.dao.HistoryDB;
import com.margretcraft.weatherforecasterv2.model.gettingApiData.ApiHolder;
import com.margretcraft.weatherforecasterv2.model.gettingApiData.OpenWeather;

public class App extends Application {
    private static App INSTANCE;

    private static ApiHolder apiHolder;
    private static HistoryDB db;

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;
        apiHolder = new ApiHolder();

        db = Room.databaseBuilder(getApplicationContext(), HistoryDB.class, "history_database")

                .build();
    }

    public static OpenWeather getOpenWeatherApi() {
        return apiHolder.getOpenWeather();
    }

    public static Context getInstance() {
        return INSTANCE;
    }

    public static HistoryDAO getHistoryDAO() {
        return db.getHistoryDAO();
    }
}

