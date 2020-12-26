package com.margretcraft.weatherforecasterv2.model.gettingApiData;

import com.margretcraft.weatherforecasterv2.model.jsonmodel.ListRequest;
import com.margretcraft.weatherforecasterv2.model.jsonmodel.WeatherRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeather {

    @GET("data/2.5/weather")
    Call<WeatherRequest> loadWeather(@Query("lat") String lat, @Query("lon") String lon, @Query("lang") String lang, @Query("appid") String keyApi);

    @GET("data/2.5/onecall")
    Call<ListRequest> loadForecast(@Query("lat") String lat, @Query("lon") String lon, @Query("lang") String lang, @Query("exclude") String exclude, @Query("appid") String keyApi);

}
