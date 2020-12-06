package com.margretcraft.weatherforecasterv2.ui.weatherUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.margretcraft.weatherforecasterv2.MainNdActivity;
import com.margretcraft.weatherforecasterv2.R;
import com.margretcraft.weatherforecasterv2.model.ForecastAdapter;
import com.margretcraft.weatherforecasterv2.model.jsonmodel.ListRequest;
import com.margretcraft.weatherforecasterv2.model.jsonmodel.Request;

public class ForecastFragment extends Fragment {
    private WeatherViewModel weatherViewModel;
    private RecyclerView recyclerView;
    private String[] days;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        weatherViewModel =
                new ViewModelProvider(this).get(WeatherViewModel.class);
        weatherViewModel.setActivity(getActivity());

        View rootView = inflater.inflate(R.layout.fragment_forecast, container, false);
        weatherViewModel.getWeatherRequestMLD().observe(getViewLifecycleOwner(), new Observer<Request>() {
            @Override
            public void onChanged(Request s) {
                updateAdapter((ListRequest) s);
            }
        });

        weatherViewModel.startGettingForecastData();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        days = ((MainNdActivity) getActivity()).getDays();

        recyclerView = getActivity().findViewById(R.id.recyclerViewHours);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    public void updateAdapter(ListRequest s) {
        if (s.isGettingSuccess()) {
            String mes = ((MainNdActivity) getActivity()).isTempmes() ? getString(R.string.tempmes1) : getString(R.string.tempmes2);
            recyclerView.setAdapter(new ForecastAdapter(getContext(), mes, days, s.getDaily()));
        } else {
        }
    }
}
