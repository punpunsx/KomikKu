package com.komikatow.komiku.ui.activityes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidnetworking.error.ANError;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.komikatow.komiku.adapter.AdapterChapterDetail;
import com.komikatow.komiku.databinding.ActivityBacaBinding;
import com.komikatow.komiku.model.ModelChapterDetail;
import com.komikatow.komiku.utils.Endpoints;
import com.komikatow.komiku.utils.Networking;
import com.komikatow.komiku.utils.NoInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class BacaActivitty extends BaseActivity <ActivityBacaBinding>{
    private final List <ModelChapterDetail <String> > list = new ArrayList<>();
    private boolean transitionStatus;
    private boolean modeBaca;
    private boolean bahasa;

    @Override
    protected ActivityBacaBinding createBinding(LayoutInflater layoutInflater) {
        return ActivityBacaBinding.inflate(layoutInflater);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDetailChapter(Endpoints.KOMIK_DETAIL_CHAPTER+getIntent().getStringExtra("endpoint"));
        getBinding().toolbar.setNavigationOnClickListener(v-> finish());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        transitionStatus = sharedPreferences.getBoolean("animasiTransisi", false);
        modeBaca = sharedPreferences.getBoolean("mode", false);
        bahasa = sharedPreferences.getBoolean("bahasa", true);

        getBinding().toolbar.setNavigationOnClickListener(v->{

            if (transitionStatus){
                finish();
                Animatoo.INSTANCE.animateSwipeRight(this);

            }else {
                finish();
            }
        });

        if (bahasa){
            getBinding().btnSebelumnya.setText("Prev Chapter");
            getBinding().btnSelanjutnya.setText("Next Chapter");
        }else {
            getBinding().btnSebelumnya.setText("Bab Sebelumnya");
            getBinding().btnSelanjutnya.setText("Bab Selanjutnya");
        }
    }

    private void getDetailChapter(String url){

        Networking.HttpsRequest(url, this, new Networking.Response() {
            @Override
            public void onHttpsResponse(JSONObject jsonObject) {

                try {

                    getBinding().toolbar.setTitle(jsonObject.getString("title"));
                    getBinding().toolbar.setSubtitle(getIntent().getStringExtra("number"));
                    JSONArray jsonArray = jsonObject.getJSONArray("images");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        ModelChapterDetail<String> modelChapterDetail = new ModelChapterDetail<>();
                        modelChapterDetail.setImage(jsonArray.getString(i));
                        list.add(modelChapterDetail);
                    }
                    onNextOrPrevChClick(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterChapterDetail(list);
            }

            @Override
            public void onHttpsError(ANError anError) {
                Toast.makeText(BacaActivitty.this, "Error saat mencoba mengambil data disebabkan : "+anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                getBinding().parentLottie.setVisibility(View.VISIBLE);
                getBinding().parentButton.setVisibility(View.GONE);
            }
        });
    }

    private void adapterChapterDetail(List<ModelChapterDetail<String>> list) {

        AdapterChapterDetail adapterChapterDetail = new AdapterChapterDetail(list, this);
        getBinding().vPager.setAdapter(adapterChapterDetail);

        if (modeBaca){
            getBinding().vPager.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        }else {
            getBinding().vPager.setLayoutManager(new LinearLayoutManager(this));
        }

    }

    private void onNextOrPrevChClick(JSONObject jsonObject) throws JSONException{

        final JSONObject chapter = jsonObject.getJSONObject("chapter");
        final String nextCh = chapter.getString("next");
        final String prevCh = chapter.getString("prev");

        if (chapter.isNull("next")){
            getBinding().btnSelanjutnya.setEnabled(false);
        }else {
            getBinding().btnSelanjutnya.setEnabled(true);
            getBinding().btnSelanjutnya.setOnClickListener(v-> refreshActivityWhiteNewData(nextCh));
        }

        if (chapter.isNull("prev")){
            getBinding().btnSebelumnya.setEnabled(false);

        }else {
            getBinding().btnSebelumnya.setEnabled(true);
            getBinding().btnSebelumnya.setOnClickListener(v-> refreshActivityWhiteNewData(prevCh));
        }

    }

    private void refreshActivityWhiteNewData(String endpoint){

        Intent intent = new Intent(this, BacaActivitty.class);
        intent.putExtra("endpoint",endpoint);


        if (transitionStatus){

            startActivity(intent);
            finish();
            Animatoo.INSTANCE.animateZoom(this);

        }else{
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        NoInternet.Companion.checkInternet(getLifecycle(), this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (transitionStatus){
            Animatoo.INSTANCE.animateSwipeRight(this);
        }

    }
}
