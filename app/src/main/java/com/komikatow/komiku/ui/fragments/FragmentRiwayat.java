package com.komikatow.komiku.ui.fragments;

import android.content.Intent;
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
import com.komikatow.komiku.ui.activityes.DetailActivity;
import com.komikatow.komiku.utils.DialogsKt;
import com.komikatow.komiku.utils.ItemRecyclerClick;
import com.komikatow.komiku.utils.OnDialogListener;

import java.util.List;

public final class FragmentRiwayat extends BaseFragment <FragmentRiwayatBinding> implements ItemRecyclerClick {

    private HistoryDbApp historyDbApp;
    private List<ModelChapter > data;
    private ModelChapter enityChapter;
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
            adapterRiwayat = new AdapterRiwayat(getContext(), data, this);

            requireActivity().runOnUiThread(() -> {
                getBinding().rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
                getBinding().rvHistory.setAdapter(adapterRiwayat);
            });

        }).start();

    }

    @Override
    public void onClickListener(int pos) {

        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("endpoint", data.get(pos).getEndPointDetail());
        startActivity(intent);

    }

    @Override
    public boolean onLongClickListener(int pos) {

        DialogsKt.setAlertDialog(requireContext(), "Hapus...", "Hapus " + data.get(pos).getNameKomik() + " Dari riwayat baca", false, new OnDialogListener() {
            @Override
            public void onOkeButton() {

                new Thread(() -> {
                    enityChapter = new ModelChapter(data.get(pos).getEndPointDetail(), data.get(pos).getDate(), data.get(pos).getNameKomik(), data.get(pos).getNemeCh(), data.get(pos).getThumbnail());
                    historyDbApp.dao().delete(enityChapter);
                    data.remove(pos);

                }).start();

                adapterRiwayat.notifyItemRemoved(pos);

            }

            @Override
            public void onCencleButton() {

            }
        });
        DialogsKt.showAlertDialog();
        return true;
    }

}
