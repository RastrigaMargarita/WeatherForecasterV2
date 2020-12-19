package com.margretcraft.weatherforecasterv2.dao;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.margretcraft.weatherforecasterv2.model.HistoryAdapter;

import java.util.Arrays;

public class DataReceiver extends BroadcastReceiver {

    HistoryAdapter observer;

    public DataReceiver() {
    }

    public void setObserver(HistoryAdapter observer) {
        this.observer = observer;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Parcelable[] result = intent.getParcelableArrayExtra("histories");
        observer.update(Arrays.copyOf(result, result.length, History[].class));

    }
}