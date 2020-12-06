package com.margretcraft.weatherforecasterv2.model.jsonmodel;


public class ListRequest implements Request {
    private Daily[] daily;
    private transient boolean gettingSuccess;

    public boolean isGettingSuccess() {
        return gettingSuccess;
    }

    public void setGettingSuccess(boolean gettingSuccess) {
        this.gettingSuccess = gettingSuccess;
    }

    public Daily[] getDaily() {
        return daily;
    }

    public void setDaily(Daily[] daily) {
        this.daily = daily;
    }

}
