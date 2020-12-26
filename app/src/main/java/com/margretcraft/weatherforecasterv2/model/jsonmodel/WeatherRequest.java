package com.margretcraft.weatherforecasterv2.model.jsonmodel;


import android.os.Parcel;

import com.google.gson.annotations.Expose;

public class WeatherRequest implements Request{
    @Expose
    private Weather[] weather;
    @Expose
    private Main main;
    @Expose
    private Wind wind;
    @Expose
    private Clouds clouds;
    @Expose
    private String name;

    public WeatherRequest() {
    }

    public WeatherRequest(Parcel in) {
        weather = in.createTypedArray(Weather.CREATOR);
        main = in.readParcelable(Main.class.getClassLoader());
        wind = in.readParcelable(Wind.class.getClassLoader());
        clouds = in.readParcelable(Clouds.class.getClassLoader());
        name = in.readString();
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
   public void writeToParcel(Parcel dest, int flags) {

        dest.writeTypedArray(weather, 0);
        dest.writeParcelable(main,0);
        dest.writeParcelable(wind,0);
        dest.writeParcelable(clouds,0);
        dest.writeString(name);
    }

    public static final Creator<WeatherRequest> CREATOR = new Creator<WeatherRequest>() {
        @Override
        public WeatherRequest createFromParcel(Parcel in) {
            return new WeatherRequest(in);
        }

        @Override
        public WeatherRequest[] newArray(int size) {
            return new WeatherRequest[size];
        }
    };
}
