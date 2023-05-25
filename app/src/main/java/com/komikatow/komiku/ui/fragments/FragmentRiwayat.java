package com.komikatow.komiku.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.komikatow.komiku.adapter.AdapterRiwayat;
import com.komikatow.komiku.databinding.FragmentRiwayatBinding;
import com.komikatow.komiku.room.dbApp.HistoryDbApp;
import com.komikatow.komiku.room.enity.ModelChapter;

import java.util.List;

public final class FragmentRiwayat extends BaseFragment <FragmentRiwayatBinding> {

    private HistoryDbApp historyDbApp;
    private List<ModelChapter > data;
    private AdapterRiwayat adapterRiwayat;

    @Override
    protected FragmentRiwayatBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentRiwayatBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getAllData();
    }

    private void getAllData(){

        new Thread(() -> {

            historyDbApp = HistoryDbApp.getInstance(getContext());
            data = historyDbApp.dao().getAllData();
            adapterRiwayat = new AdapterRiwayat(getContext(), data);

            requireActivity().runOnUiThread(() -> {
                getBinding().rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
                getBinding().rvHistory.setAdapter(adapterRiwayat);
            });

        }).start();

    }
}
