package com.margretcraft.weatherforecasterv2.model.jsonmodel;


import android.os.Parcel;

public class ListRequest implements Request {
    private Daily[] daily;

    public ListRequest() {
    }

    public ListRequest(Parcel in) {
        daily = in.createTypedArray(Daily.CREATOR);
    }

    public Daily[] getDaily() {
        return daily;
    }

    public void setDaily(Daily[] daily) {
        this.daily = daily;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(daily, 0);
    }

    public static final Creator<ListRequest> CREATOR = new Creator<ListRequest>() {
        @Override
        public ListRequest createFromParcel(Parcel in) {
            return new ListRequest(in);
        }

        @Override
        public ListRequest[] newArray(int size) {
            return new ListRequest[size];
        }
    };
}
