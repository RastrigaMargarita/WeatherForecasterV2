package com.margretcraft.weatherforecasterv2.ui.settingsUI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.margretcraft.weatherforecasterv2.MainNdActivity;
import com.margretcraft.weatherforecasterv2.R;

public class SettingsFragment extends Fragment {

    private RadioButton radioButtonMS;
    private RadioButton radioButtonKH;
    private RadioButton radioButtonC;
    private RadioButton radioButtonK;
    private RadioButton radioButtonV;
    private RadioButton radioButtonG;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        SharedPreferences sharedPref = ((MainNdActivity) getActivity()).getSharedPref();

        radioButtonMS = root.findViewById(R.id.radioButtonMS);
        radioButtonKH = root.findViewById(R.id.radioButtonKH);
        radioButtonC = root.findViewById(R.id.radioButtonC);
        radioButtonK = root.findViewById(R.id.radioButtonK);
        radioButtonV = root.findViewById(R.id.radioButtonV);
        radioButtonG = root.findViewById(R.id.radioButtonG);

        radioButtonMS.setChecked(sharedPref.getBoolean("windmes", true));
        radioButtonKH.setChecked(!sharedPref.getBoolean("windmes", true));
        radioButtonC.setChecked(sharedPref.getBoolean("tempmes", true));
        radioButtonK.setChecked(!sharedPref.getBoolean("tempmes", true));
        radioButtonV.setChecked(sharedPref.getBoolean("theme", true));
        radioButtonG.setChecked(!sharedPref.getBoolean("theme", true));

        radioButtonV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButtonV.isChecked()) {
                    ((MainNdActivity) getActivity()).setSetting("theme", true);
                }
                getActivity().recreate();
            }
        });
        radioButtonG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButtonG.isChecked()) {
                    ((MainNdActivity) getActivity()).setSetting("theme", false);
                }
                getActivity().recreate();
            }
        });
        radioButtonMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButtonMS.isChecked()) {
                    ((MainNdActivity) getActivity()).setSetting("windmes", true);
                }
            }
        });
        radioButtonKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButtonKH.isChecked()) {
                    ((MainNdActivity) getActivity()).setSetting("windmes", false);
                }
            }
        });
        radioButtonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButtonC.isChecked()) {
                    ((MainNdActivity) getActivity()).setSetting("tempmes", true);
                }
            }
        });
        radioButtonK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButtonK.isChecked()) {
                    ((MainNdActivity) getActivity()).setSetting("tempmes", false);
                }
            }
        });

        return root;
    }
}