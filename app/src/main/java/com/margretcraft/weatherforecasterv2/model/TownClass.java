package com.margretcraft.weatherforecasterv2.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TownClass implements Parcelable {
    private String name;
    private String point;
    private String timeZone;

    public TownClass(String name, String point, String timeZone) {
        this.name = name;
        this.point = point;
        this.timeZone = timeZone;
    }

    protected TownClass(Parcel in) {
        name = in.readString();
        point = in.readString();
        timeZone = in.readString();
    }

    public static final Creator<TownClass> CREATOR = new Creator<TownClass>() {
        @Override
        public TownClass createFromParcel(Parcel in) {
            return new TownClass(in);
        }

        @Override
        public TownClass[] newArray(int size) {
            return new TownClass[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getPoint() {
        return point;
    }

    public String getTimeZone() {
        return timeZone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(point);
        dest.writeString(timeZone);
    }
}
