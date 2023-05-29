package com.komikatow.komiku.ui.activityes;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.komikatow.komiku.databinding.AdvanceSetingBinding;
import com.komikatow.komiku.room.dbApp.AdvanceDbApp;
import com.komikatow.komiku.room.enity.AdvanceSizeEnity;

public final class AdvanceActivity extends BaseActivity <AdvanceSetingBinding> {
    private AdvanceDbApp database;


    @Override
    protected AdvanceSetingBinding createBinding(LayoutInflater layoutInflater) {
        return AdvanceSetingBinding.inflate(layoutInflater);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getEditTextInput();
        getBinding().toolbar.setNavigationOnClickListener(v->{

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            boolean isTransition = sharedPreferences.getBoolean("animasiTransisi", false);

            if (isTransition){
                finish();
                Animatoo.INSTANCE.animateSwipeRight(this);
            }else {
                finish();
            }

        });
    }
    private void getEditTextInput(){

        getBinding().btnOk.setOnClickListener(v->{

            if (!getBinding().slider.getText().toString().isEmpty() && !getBinding().detail.getText().toString().isEmpty()){
                setUpDb();
                finish();

            }else {
                Toast.makeText(this, "Silahkan lengkapi data terlebih dahulu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpDb(){

        new Thread(() -> {

            database = AdvanceDbApp.getInstance(getApplicationContext());
            AdvanceSizeEnity enity = new AdvanceSizeEnity(getBinding().slider.getText().toString(), getBinding().detail.getText().toString());

            if (database.dao().checkIfSliderExist(enity.getSlider())){

                database.dao().delete(enity);
                database.dao().insert(enity);
            }else {
                database.dao().insert(enity);
            }


            if (database.dao().checkIfDetailExist(enity.getDetail())){

                database.dao().delete(enity);
                   database.dao().insert(enity);

            }else {
                database.dao().insert(enity);
            }


        }).start();

    }
}
