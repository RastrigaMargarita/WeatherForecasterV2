package com.margretcraft.weatherforecasterv2.ui.historyUI;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.margretcraft.weatherforecasterv2.MainNdActivity;
import com.margretcraft.weatherforecasterv2.R;
import com.margretcraft.weatherforecasterv2.broadcast.DataReceiver;
import com.margretcraft.weatherforecasterv2.model.HistoryAdapter;

public class HistoryFragment extends Fragment {

    private RecyclerView historyRecyclerView;
    private HistoryAdapter historyAdapter;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        historyRecyclerView = getActivity().findViewById(R.id.history_recycler_view);
        historyRecyclerView.setHasFixedSize(true);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        HistoryAdapter ha = new HistoryAdapter(null, LayoutInflater.from(getContext()), ((MainNdActivity) getActivity()).isTempmes());
        historyRecyclerView.setAdapter(ha);

        DataReceiver dr = new DataReceiver();
        dr.setObserver(ha);
        getActivity().registerReceiver(dr, new IntentFilter("history"));

    }

}