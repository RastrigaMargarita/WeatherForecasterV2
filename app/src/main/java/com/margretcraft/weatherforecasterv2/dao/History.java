package com.margretcraft.weatherforecasterv2.dao;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class History implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "data")
    public long data;

    @ColumnInfo(name = "temp")
    public float temp;

    @ColumnInfo(name = "town")
    public String town;

    public History(long data, float temp, String town) {
        this.data = data;
        this.temp = temp;
        this.town = town;
    }

    public History() {
    }

    protected History(Parcel in) {
        id = in.readLong();
        data = in.readLong();
        temp = in.readFloat();
        town = in.readString();
    }

    public static final Creator<History> CREATOR = new Creator<History>() {
        @Override
        public History createFromParcel(Parcel in) {
            return new History(in);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(data);
        dest.writeFloat(temp);
        dest.writeString(town);
    }
}
