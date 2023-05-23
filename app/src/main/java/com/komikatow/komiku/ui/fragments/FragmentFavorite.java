package com.komikatow.komiku.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.komikatow.komiku.databinding.FragmentFavoriteBinding;

public final class FragmentFavorite extends BaseFragment <FragmentFavoriteBinding> {
    @Override
    protected FragmentFavoriteBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentFavoriteBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
