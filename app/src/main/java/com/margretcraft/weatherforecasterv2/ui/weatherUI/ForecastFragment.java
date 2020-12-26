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

import com.margretcraft.weatherforecasterv2.R;
import com.margretcraft.weatherforecasterv2.model.ForecastAdapter;

public class ForecastFragment extends Fragment {
    private WeatherViewModel weatherViewModel;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        weatherViewModel =
                new ViewModelProvider(this).get(WeatherViewModel.class);
        weatherViewModel.setActivity(getActivity());

        View rootView = inflater.inflate(R.layout.fragment_forecast, container, false);

        weatherViewModel.getForecastAdapterMLD().observe(getViewLifecycleOwner(), new Observer<ForecastAdapter>() {
            @Override
            public void onChanged(ForecastAdapter s) {
                recyclerView.setAdapter(s);
            }
        });

        weatherViewModel.startGettingData(1);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = getActivity().findViewById(R.id.recyclerViewHours);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
