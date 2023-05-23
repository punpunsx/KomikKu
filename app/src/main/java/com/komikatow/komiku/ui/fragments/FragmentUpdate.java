package com.komikatow.komiku.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.komikatow.komiku.databinding.FragmentUpdateBinding;

public final class FragmentUpdate extends BaseFragment <FragmentUpdateBinding> {

    @Override
    protected FragmentUpdateBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentUpdateBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
