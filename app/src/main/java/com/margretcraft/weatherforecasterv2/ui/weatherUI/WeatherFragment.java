package com.margretcraft.weatherforecasterv2.ui.weatherUI;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

import com.margretcraft.weatherforecasterv2.MainNdActivity;
import com.margretcraft.weatherforecasterv2.R;
import com.margretcraft.weatherforecasterv2.ui.CompassView;
import com.squareup.picasso.Picasso;

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
    private CompassView compassView;
    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener;
    private Sensor defaultMagneticSensor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        weatherViewModel =
                new ViewModelProvider(this).get(WeatherViewModel.class);
        weatherViewModel.setActivity(getActivity());

        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);

        ImageView imageViewFon = rootView.findViewById(R.id.imageViewFon);
        Picasso.get()
                .load("https://images.unsplash.com/photo-1483315303845-319eb8428b29?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=762&q=80")
                .fit()
                .into(imageViewFon);

        Date showDay = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy");
        textViewTown = rootView.findViewById(R.id.TextViewTown);
        textViewTown.setText(((MainNdActivity) getActivity()).getCurrentTown().getName().replaceFirst(" ", "\n"));
        textViewData = rootView.findViewById(R.id.textViewData);
        textViewData.setText(sdf.format(showDay));

        textViewMinMax = rootView.findViewById(R.id.textViewMinMax);
        weatherViewModel.getMinMaxMLD().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textViewMinMax.setText(s);
                //   setValues((WeatherRequest) s);
            }
        });
        textViewState = rootView.findViewById(R.id.textViewState);
        weatherViewModel.getStateMLD().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textViewState.setText(s);
            }
        });
        textViewTemperature = rootView.findViewById(R.id.textViewTemperature);
        weatherViewModel.getTempMLD().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textViewTemperature.setText(s);
            }
        });
        textViewWind = rootView.findViewById(R.id.textViewWind);
        weatherViewModel.getWindMLD().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textViewWind.setText(s);
            }
        });
        imageViewWind = rootView.findViewById(R.id.imageViewWind);
        weatherViewModel.getWindImageMLD().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer s) {
                imageViewWind.setImageResource(s);
            }
        });


        weatherViewModel.startGettingData(0);

        compassView = rootView.findViewById(R.id.compassView);

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        defaultMagneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                compassView.setAngle(event.values[0]);
                compassView.invalidate();
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        if (defaultMagneticSensor != null) {
            compassView.setSensorValid(true);
        }

        return rootView;
    }


    @Override
    public void onPause() {
        sensorManager.unregisterListener(sensorEventListener);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, defaultMagneticSensor,
                SensorManager.SENSOR_DELAY_UI);
    }

}