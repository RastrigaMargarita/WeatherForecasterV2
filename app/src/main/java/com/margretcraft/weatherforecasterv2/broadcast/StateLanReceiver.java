package com.margretcraft.weatherforecasterv2.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.margretcraft.weatherforecasterv2.MainNdActivity;

public class StateLanReceiver extends BroadcastReceiver {

    MainNdActivity observer;

    public void setObserver(MainNdActivity observer) {
        this.observer = observer;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        observer.isLanChanged();
    }
}