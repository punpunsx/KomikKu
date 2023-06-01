package com.komikatow.komiku.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.komikatow.komiku.adapter.AdapterFavorite;
import com.komikatow.komiku.databinding.FragmentFavoriteBinding;
import com.komikatow.komiku.room.dbApp.FavoriteDbApp;
import com.komikatow.komiku.room.enity.FavoriteEnity;
import com.komikatow.komiku.ui.activityes.DetailActivity;
import com.komikatow.komiku.utils.DialogsKt;
import com.komikatow.komiku.utils.ItemRecyclerClick;
import com.komikatow.komiku.utils.OnDialogListener;

import java.util.List;

public final class FragmentFavorite extends BaseFragment <FragmentFavoriteBinding>  {

    private FavoriteDbApp database;
    private List<FavoriteEnity> allData;
    private AdapterFavorite adapterFavorite;
    private boolean isTransition;
    private boolean getBahasa;
    private String title, msg, from;

    @Override
    protected FragmentFavoriteBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentFavoriteBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        isTransition = sharedPreferences.getBoolean("animasiTransisi", false);
        getBahasa = sharedPreferences.getBoolean("bahasa", true);

        getConfigLangague();
    }

    private void getAllItem(){

        new Thread(() -> {

            database = FavoriteDbApp.getInstance(getContext());
            allData = database.dao().getAllData();

            if (allData.isEmpty()){
                requireActivity().runOnUiThread(()-> getBinding().parentLottie.setVisibility(View.VISIBLE));

            }else {
                requireActivity().runOnUiThread(()-> getBinding().parentLottie.setVisibility(View.GONE));
            }

            adapterFavorite = new AdapterFavorite(allData, getContext(), new ItemRecyclerClick() {
                @Override
                public void onClickListener(int pos) {

                    Intent intent = new Intent(getContext(), DetailActivity.class);
                    intent.putExtra("endpoint", allData.get(pos).getEndpoint());

                    if (isTransition){
                        startActivity(intent);
                        Animatoo.INSTANCE.animateZoom(requireContext());

                    }else {
                        startActivity(intent);
                    }

                }

                @Override
                public boolean onLongClickListener(int position) {

                    DialogsKt.setAlertDialog(requireContext(), title, msg + allData.get(position).getTitle() + from, false, new OnDialogListener() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onOkeButton() {

                            new Thread(() ->{
                                database.dao().deleteData(new FavoriteEnity(allData.get(position).getEndpoint(), allData.get(position).getTitle(), allData.get(position).getThumbnail()));
                                allData.remove(position);
                            }).start();
                            adapterFavorite.notifyItemRemoved(position);

                        }

                        @Override
                        public void onCencleButton() {

                        }
                    });
                    DialogsKt.showAlertDialog();
                    return true;
                }
            });

            requireActivity().runOnUiThread(() -> {
                getBinding().rvFav.setLayoutManager(new GridLayoutManager(getContext(), 3));
                getBinding().rvFav.setAdapter(adapterFavorite);

                if (getBahasa){
                    getBinding().itemNull.setText("You haven't added your favorite comic, please click the favorite icon on the comic details to save data\n To delete data, please click on the comic whose data you want to delete");
                }
            });


        }).start();

    }

    private void getConfigLangague(){
        if (getBahasa){
            title = "Delete";
            msg = "Remove comic : ";
            from = " from favorite";

        }else {
            title = "Hapus";
            msg = "Hapus : ";
            from = " dari favorite";
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        getAllItem();
    }
}
