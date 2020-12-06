package com.margretcraft.weatherforecasterv2.ui.weatherUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.margretcraft.weatherforecasterv2.MainNdActivity;
import com.margretcraft.weatherforecasterv2.R;
import com.margretcraft.weatherforecasterv2.model.jsonmodel.Request;
import com.margretcraft.weatherforecasterv2.model.jsonmodel.WeatherRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherFragment extends Fragment {

    private WeatherViewModel weatherViewModel;
    private TextView textViewData;
    private TextView textViewMinMax;
    private TextView textViewState;
    private TextView textViewTemperature;
    private TextView textViewTown;
    private TextView textViewWind;
    private ImageView imageViewWind;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        weatherViewModel =
                new ViewModelProvider(this).get(WeatherViewModel.class);
        weatherViewModel.setActivity(getActivity());

        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);

        textViewData = rootView.findViewById(R.id.textViewData);
        textViewMinMax = rootView.findViewById(R.id.textViewMinMax);
        textViewState = rootView.findViewById(R.id.textViewState);
        textViewTemperature = rootView.findViewById(R.id.textViewTemperature);
        textViewTown = rootView.findViewById(R.id.TextViewTown);
        textViewWind = rootView.findViewById(R.id.textViewWind);
        imageViewWind = rootView.findViewById(R.id.imageViewWind);

        weatherViewModel.getWeatherRequestMLD().observe(getViewLifecycleOwner(), new Observer<Request>() {
            @Override
            public void onChanged(Request s) {
                setValues((WeatherRequest) s);
            }
        });
        weatherViewModel.startGettingWeatherData();

        return rootView;
    }

    public void setValues(WeatherRequest wr) {
        if (wr.isGettingSuccess()) {
            Date showDay = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy");

            textViewTown.setText(((MainNdActivity) getActivity()).getCurrentTown().getName().replaceFirst(" ", "\n"));
            textViewData.setText(sdf.format(showDay));

            StringBuilder sb = new StringBuilder();
            if (((MainNdActivity) getActivity()).isTempmes()) {
                sb.append("%d").append(getString(R.string.tempmes1)).append("...%d").append(getString(R.string.tempmes1));
                textViewMinMax.setText(String.format(sb.toString(), (int) ((wr.getMain().getTemp_min()) - getResources().getInteger(R.integer.transferT)), (int) ((wr.getMain().getTemp_max()) - getResources().getInteger(R.integer.transferT))));
                textViewTemperature.setText("" + (int) (wr.getMain().getTemp() - getResources().getInteger(R.integer.transferT)) + getString(R.string.tempmes1));
            } else {
                sb.append("%.2f").append(getString(R.string.tempmes2)).append("...%.2f").append(getString(R.string.tempmes2));
                textViewMinMax.setText(String.format(sb.toString(), (wr.getMain().getTemp_min()), (wr.getMain().getTemp_max())));
                textViewTemperature.setText("" + wr.getMain().getTemp() + getString(R.string.tempmes2));
            }

            sb.delete(0, sb.length());
            if (((MainNdActivity) getActivity()).isWindmes()) {
                textViewWind.setText(String.format(sb.append("%.1f ").append(getString(R.string.windmes1)).toString(), wr.getWind().getSpeed()));
            } else {
                textViewWind.setText(String.format(sb.append("%.1f").append(getString(R.string.windmes2)).toString(), wr.getWind().getSpeed() * 0.10 * getResources().getInteger(R.integer.transferW)));
            }
            sb.delete(0, sb.length());

            imageViewWind.setImageResource(getResources().getIdentifier("wd" + (Math.round(wr.getWind().getDeg() * 1.0 / 45) + 1), "drawable", getActivity().getPackageName()));
            textViewState.setText(wr.getWeather()[0].getMain() + "\n" + wr.getWeather()[0].getDescription());

        } else {
            Snackbar.make(
                    imageViewWind, R.string.errorGettingData, BaseTransientBottomBar.LENGTH_LONG).show();

        }
    }
}