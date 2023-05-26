package com.komikatow.komiku.ui.activityes;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.komikatow.komiku.databinding.ActivityTeknologiBinding;

public class TeknologiActivity extends BaseActivity<ActivityTeknologiBinding>{

    @Override
    protected ActivityTeknologiBinding createBinding(LayoutInflater layoutInflater) {
        return ActivityTeknologiBinding.inflate(layoutInflater);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
