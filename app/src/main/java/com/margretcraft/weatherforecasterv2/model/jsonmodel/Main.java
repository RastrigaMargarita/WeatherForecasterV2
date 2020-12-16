package com.margretcraft.weatherforecasterv2.model.jsonmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class Main implements Parcelable {
    @Expose
    private float temp;
    @Expose
    private int pressure;
    @Expose
    private int humidity;
    @Expose
    private float temp_min;
    @Expose
    private float temp_max;

    protected Main(Parcel in) {
        temp = in.readFloat();
        pressure = in.readInt();
        humidity = in.readInt();
        temp_min = in.readFloat();
        temp_max = in.readFloat();
    }

    public static final Creator<Main> CREATOR = new Creator<Main>() {
        @Override
        public Main createFromParcel(Parcel in) {
            return new Main(in);
        }

        @Override
        public Main[] newArray(int size) {
            return new Main[size];
        }
    };

    public float getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(int temp_min) {
        this.temp_min = temp_min;
    }

    public float getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(int temp_max) {
        this.temp_max = temp_max;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(temp);
        dest.writeInt(pressure);
        dest.writeInt(humidity);
        dest.writeFloat(temp_min);
        dest.writeFloat(temp_max);
    }
}
