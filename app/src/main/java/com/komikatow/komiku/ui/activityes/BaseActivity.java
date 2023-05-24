package com.komikatow.komiku.ui.activityes;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

public abstract class BaseActivity < T extends ViewBinding > extends AppCompatActivity {
    private T binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = createBinding(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    protected T getBinding() {
        return binding;
    }

    protected abstract T createBinding(LayoutInflater layoutInflater);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}
