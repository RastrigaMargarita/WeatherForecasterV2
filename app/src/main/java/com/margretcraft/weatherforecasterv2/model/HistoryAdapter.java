package com.margretcraft.weatherforecasterv2.model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.recyclerview.widget.RecyclerView;

import com.margretcraft.weatherforecasterv2.App;
import com.margretcraft.weatherforecasterv2.R;
import com.margretcraft.weatherforecasterv2.dao.DBService;
import com.margretcraft.weatherforecasterv2.dao.History;

import java.text.SimpleDateFormat;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.RecordHolder> {
    private History[] listHistory;
    private LayoutInflater inflater;
    private boolean tempmes;
    SimpleDateFormat sdf;

    public HistoryAdapter(History[] listHistory, LayoutInflater inflater, boolean tempmes) {

        this.inflater = inflater;
        this.tempmes = tempmes;
        sdf = new SimpleDateFormat("d MMM yyyy");

        Intent intent = new Intent();
        intent.putExtra("action", "0");
        JobIntentService.enqueueWork(App.getInstance().getApplicationContext(), DBService.class, 0, intent);
    }

    @NonNull
    @Override
    public HistoryAdapter.RecordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryAdapter.RecordHolder(inflater.inflate(R.layout.history_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.RecordHolder holder, int position) {
        holder.setData(listHistory[position]);
    }

    @Override
    public int getItemCount() {
        if (listHistory == null) {
            return 0;
        } else {
            return listHistory.length;
        }
    }

    public class RecordHolder extends RecyclerView.ViewHolder {
        History history;
        TextView textViewData;
        TextView textViewTemp;
        TextView textViewTown;

        public RecordHolder(@NonNull View itemView) {
            super(itemView);
            textViewData = itemView.findViewById(R.id.textViewDataHC);
            textViewTemp = itemView.findViewById(R.id.textViewTempHC);
            textViewTown = itemView.findViewById(R.id.textViewTownHC);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(view.getContext(), view);
                    popup.inflate(R.menu.cont_menu);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            deleteItem();
                            return true;
                        }
                    });
                    popup.show();
                }
            });
        }

        private void deleteItem() {
            Intent intent = new Intent();
            intent.putExtra("history", history);
            intent.putExtra("action", "1");
            JobIntentService.enqueueWork(textViewData.getContext(), DBService.class, 0, intent);
            intent = new Intent();
            intent.putExtra("action", "0");
            JobIntentService.enqueueWork(textViewData.getContext(), DBService.class, 0, intent);
        }

        public void setData(History record) {
            history = record;
            textViewData.setText("" + sdf.format(record.data));
            textViewTemp.setText("" + (tempmes ? String.format("%d", Math.round(record.temp - inflater.getContext().getResources().getInteger(R.integer.transferT)))
                    : String.format("%.2f", record.temp)));
            textViewTown.setText(record.town);
        }
    }

    public void update(History[] listHistory) {
        this.listHistory = listHistory;
        notifyDataSetChanged();
    }
}
