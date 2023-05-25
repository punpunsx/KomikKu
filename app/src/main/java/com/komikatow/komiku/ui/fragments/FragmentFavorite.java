package com.komikatow.komiku.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

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

    @Override
    protected FragmentFavoriteBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentFavoriteBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getAllItem();
    }

    private void getAllItem(){

        new Thread(() -> {

            database = FavoriteDbApp.getInstance(getContext());
            allData = database.dao().getAllData();

            adapterFavorite = new AdapterFavorite(allData, getContext(), new ItemRecyclerClick() {
                @Override
                public void onClickListener(int pos) {

                    Intent intent = new Intent(getContext(), DetailActivity.class);
                    intent.putExtra("endpoint", allData.get(pos).getEndpoint());
                    startActivity(intent);

                }

                @Override
                public boolean onLongClickListener(int position) {

                    DialogsKt.setAlertDialog(requireContext(), "Hapus", "Hapus komik : " + allData.get(position).getTitle() + " Dari favoritre", false, new OnDialogListener() {
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
            });


        }).start();

    }

}
