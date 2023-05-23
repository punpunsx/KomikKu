package com.komikatow.komiku.ui.activity;


import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.komikatow.komiku.databinding.ActivityBacaBinding;

public final class BacaActivitty extends BaseActivity <ActivityBacaBinding>{

    @Override
    protected ActivityBacaBinding createBinding(LayoutInflater layoutInflater) {
        return ActivityBacaBinding.inflate(layoutInflater);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
