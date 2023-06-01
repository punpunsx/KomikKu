package com.komikatow.komiku.ui.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
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

    private SharedPreferences sharedPreferences;
    private HistoryDbApp historyDbApp;
    private List<ModelChapter > data;
    private ModelChapter enityChapter;
    private AdapterRiwayat adapterRiwayat;
    private boolean getBahasa;
    private String title,msg,from;


    @Override
    protected FragmentRiwayatBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentRiwayatBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        getBahasa = sharedPreferences.getBoolean("bahasa", true);


        getConfigLangague();

    }

    private void getAllData(){

        new Thread(() -> {

            historyDbApp = HistoryDbApp.getInstance(getContext());
            data = historyDbApp.dao().getAllData();
            adapterRiwayat = new AdapterRiwayat(getContext(), data, this);

            requireActivity().runOnUiThread(() -> {
                getBinding().rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
                getBinding().rvHistory.setAdapter(adapterRiwayat);


                if (getBahasa){
                    getBinding().itemNull.setText("You haven't read the comic, please read it to save data\n To delete data, please press the comic you want to delete the data for");
                }
            });

            if (data.isEmpty()){
                requireActivity().runOnUiThread(()-> getBinding().parentLottie.setVisibility(View.VISIBLE));

            }else {
                requireActivity().runOnUiThread(()-> getBinding().parentLottie.setVisibility(View.GONE));
            }

        }).start();

    }

    @Override
    public void onClickListener(int pos) {

        boolean isTransition = sharedPreferences.getBoolean("animasiTransisi", false);
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("endpoint", data.get(pos).getEndPointDetail());

        if (isTransition){
            startActivity(intent);
            Animatoo.INSTANCE.animateZoom(requireContext());

        }else {
            startActivity(intent);
        }

    }

    @Override
    public boolean onLongClickListener(int pos) {

        DialogsKt.setAlertDialog(requireContext(), title, msg + data.get(pos).getNameKomik() + from, false, new OnDialogListener() {
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

    private void getConfigLangague(){
        if (getBahasa){
            title = "Delete";
            msg = "Remove comic : ";
            from = " from history";

        }else {
            title = "Hapus";
            msg = "Hapus : ";
            from = " dari riwayat baca";
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllData();
    }
}
