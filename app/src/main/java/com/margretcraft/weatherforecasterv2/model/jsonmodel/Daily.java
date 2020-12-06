package com.margretcraft.weatherforecasterv2.model.jsonmodel;

public class Daily {
    private Temp temp;
    private double wind_speed;
    private int wind_deg;
    private Weather[] weather;

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public double getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(double wind_speed) {
        this.wind_speed = wind_speed;
    }

    public int getWind_deg() {
        return wind_deg;
    }

    public void setWind_deg(int wind_deg) {
        this.wind_deg = wind_deg;
    }

    public Weather[] getWeathers() {
        return weather;
    }

    public void setWeathers(Weather[] weathers) {
        this.weather = weathers;
    }
}
