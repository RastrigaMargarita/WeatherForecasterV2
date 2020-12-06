package com.margretcraft.weatherforecasterv2.model.jsonmodel;

public class WeatherRequest implements Request{
    private Weather[] weather;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private String name;
    private transient boolean gettingSuccess;

    public boolean isGettingSuccess() {
        return gettingSuccess;
    }

    public void setGettingSuccess(boolean gettingSuccess) {
        this.gettingSuccess = gettingSuccess;
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

}
