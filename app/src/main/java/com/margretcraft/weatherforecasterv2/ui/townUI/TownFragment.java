package com.margretcraft.weatherforecasterv2.ui.townUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.margretcraft.weatherforecasterv2.MainNdActivity;
import com.margretcraft.weatherforecasterv2.R;
import com.margretcraft.weatherforecasterv2.model.TownAdapter;
import com.margretcraft.weatherforecasterv2.model.TownClass;

import java.util.ArrayList;

public class TownFragment extends Fragment {
    private TownAdapter arrayAdapter;
    private String[] listTown;
    private String[] listTownPoint;
    private String[] listTimeZone;
    private ArrayList<TownClass> listTownClass;
    private RecyclerView listViewTown;
    private SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_town, container, false);

        listViewTown = rootView.findViewById(R.id.ListViewTown);
        ititTownClassList();
        listViewTown.setHasFixedSize(true);
        listViewTown.setLayoutManager(new LinearLayoutManager(getContext()));
        arrayAdapter = new TownAdapter(listTownClass);

        arrayAdapter.SetOnItemClickListener(new TownAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                searchView.setQuery("", false);
                searchView.clearFocus();
                searchView.setIconified(true);
                ((MainNdActivity) getActivity()).onTownChoose(listTownClass.get(position));
            }
        });
        listViewTown.setAdapter(arrayAdapter);

        searchView = (SearchView) ((MainNdActivity) getActivity()).getToolbar().getMenu().findItem(R.id.app_bar_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String searchingTown = query.trim().toLowerCase();
                boolean findtown = false;
                for (int i = 0; i < listTown.length; i++) {

                    if (listTown[i].toLowerCase().contains(searchingTown)) {
                        findtown = true;
                        listViewTown.smoothScrollToPosition(i);
                        arrayAdapter.setSelectedPosition(i);
                        arrayAdapter.notifyDataSetChanged();
                        break;
                    }
                }
                if (!findtown) {
                    Snackbar.make(rootView, "Город не найден!", Snackbar.LENGTH_LONG)
                            .show();
                    arrayAdapter.setSelectedPosition(100);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.setSelectedPosition(100);
                return false;
            }
        });

        return rootView;
    }

    private void ititTownClassList() {
        listTownClass = new ArrayList<>();
        listTown = getResources().getStringArray(R.array.towns);
        listTownPoint = getResources().getStringArray(R.array.points);
        listTimeZone = getResources().getStringArray(R.array.zones);
        for (int i = 0; i < listTown.length; i++) {
            listTownClass.add(new TownClass(listTown[i], listTownPoint[i], listTimeZone[i]));
        }
    }
}