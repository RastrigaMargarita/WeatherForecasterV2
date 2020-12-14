package com.margretcraft.weatherforecasterv2.model.jsonmodel;

import android.os.Parcel;
import android.os.Parcelable;

public class Daily implements Parcelable {
    private Temp temp;
    private double wind_speed;
    private int wind_deg;
    private Weather[] weather;

    protected Daily(Parcel in) {
        temp = in.readParcelable(Temp.class.getClassLoader());
        wind_speed = in.readDouble();
        wind_deg = in.readInt();
        weather = in.createTypedArray(Weather.CREATOR);
    }

    public static final Creator<Daily> CREATOR = new Creator<Daily>() {
        @Override
        public Daily createFromParcel(Parcel in) {
            return new Daily(in);
        }

        @Override
        public Daily[] newArray(int size) {
            return new Daily[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(temp, 0);
        dest.writeDouble(wind_speed);
        dest.writeInt(wind_deg);
        dest.writeTypedArray(weather, 0);
    }
}
