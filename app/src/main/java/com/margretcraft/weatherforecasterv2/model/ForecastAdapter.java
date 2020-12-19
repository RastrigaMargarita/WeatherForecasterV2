package com.margretcraft.weatherforecasterv2.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.margretcraft.weatherforecasterv2.R;
import com.margretcraft.weatherforecasterv2.model.jsonmodel.Daily;


public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.HoursHolder> {
    private String[] days;
    private Daily[] temps;
    private boolean tempmes;
    private LayoutInflater inflater;

    public ForecastAdapter(Context context, boolean tempmes, String[] days, Daily[] temps) {
        inflater = LayoutInflater.from(context);
        this.tempmes = tempmes;
        this.days = days;
        this.temps = temps;
    }

    public HoursHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HoursHolder(inflater.inflate(R.layout.forecast_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HoursHolder holder, int position) {
        if (temps != null) {
            if (tempmes) {
                holder.setData(days[position],
                        String.format("   %d" + inflater.getContext().getString(R.string.tempmes1), Math.round(temps[position].getTemp().getDay() - inflater.getContext().getResources().getInteger(R.integer.transferT))),
                        getWindPower(temps[position].getWind_speed()),
                        temps[position].getWeathers()[0].getDescription());
            } else {
                holder.setData(days[position],
                        String.format("   %.2f" + inflater.getContext().getString(R.string.tempmes2), temps[position].getTemp().getDay()),
                        getWindPower(temps[position].getWind_speed()),
                        temps[position].getWeathers()[0].getDescription());
            }
        }
    }

    private String getWindPower(double wind_speed) {
        if (wind_speed < 5) {
            return inflater.getContext().getString(R.string.weakwind);
        } else if (wind_speed < 10) {
            return inflater.getContext().getString(R.string.mediumwind);
        } else if (wind_speed < 20) {
            return inflater.getContext().getString(R.string.hardwind);
        } else if (wind_speed < 30) {
            return inflater.getContext().getString(R.string.storm);
        } else return inflater.getContext().getString(R.string.hurricane);
    }

    @Override
    public int getItemCount() {
        return days.length;
    }

    class HoursHolder extends RecyclerView.ViewHolder {
        TextView textViewhour;
        TextView textViewTemp;
        TextView textViewWindD;
        TextView textViewDesc;

        public HoursHolder(@NonNull View itemView) {
            super(itemView);
            textViewhour = itemView.findViewById(R.id.textViewhour);
            textViewTemp = itemView.findViewById(R.id.textViewTemp);
            textViewWindD = itemView.findViewById(R.id.textViewWindD);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
        }

        public void setData(String hour, String format, String windD, String Desc) {
            textViewhour.setText(hour);
            textViewTemp.setText(format);
            textViewWindD.setText(windD);
            if (Desc.length() > 3) {
                textViewDesc.setText(Desc.substring(0, 1).toUpperCase() + Desc.substring(1));
            }
        }
    }
}

