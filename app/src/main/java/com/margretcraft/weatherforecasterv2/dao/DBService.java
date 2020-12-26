package com.margretcraft.weatherforecasterv2.dao;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.margretcraft.weatherforecasterv2.App;

public class DBService extends JobIntentService {
    HistoryDAO historyDAO = App.getHistoryDAO();

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        switch (intent.getStringExtra("action")) {
            case "0":
                History[] histories = (History[]) historyDAO.selectAll();
                Intent broadcastIntent = new Intent("history");
                broadcastIntent.putExtra("histories", histories);
                sendBroadcast(broadcastIntent);
                break;
            case "1":
                History history = intent.getParcelableExtra("history");
                historyDAO.deleteHistoryRecord(history);
                break;
        }

    }

}