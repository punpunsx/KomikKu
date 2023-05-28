package com.komikatow.komiku.ui.activityes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;

import com.androidnetworking.error.ANError;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.komikatow.komiku.adapter.AdapterKomik;
import com.komikatow.komiku.databinding.ActivityGenreDetailBinding;
import com.komikatow.komiku.model.ModelBaseKomik;
import com.komikatow.komiku.utils.DialogsKt;
import com.komikatow.komiku.utils.Endpoints;
import com.komikatow.komiku.utils.Networking;
import com.komikatow.komiku.utils.NoInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class GenreDetail extends BaseActivity <ActivityGenreDetailBinding> {
    private final List <ModelBaseKomik<String>> genreList = new ArrayList<>();
    private int pageCount = 1;
    private String url ;
    private boolean isTransition;
    private boolean getBahasa;


    @Override
    protected ActivityGenreDetailBinding createBinding(LayoutInflater layoutInflater) {
        return ActivityGenreDetailBinding.inflate(layoutInflater);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getBinding().toolbar.setNavigationOnClickListener(v-> finish());
        getGenreDetail();
        navigateToAnotherPage();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        isTransition = sharedPreferences.getBoolean("animasiTransisi", false);
        getBahasa = sharedPreferences.getBoolean("bahasa", true);

        if (getBahasa){
            DialogsKt.setDialogLoading(this, "Loading...", "Loading content please wait a moment", false);
            DialogsKt.showDialogLoading();

        }else {
            DialogsKt.setDialogLoading(this, "Loading...", "Sedang memuat konten mohon tunggu", false);
            DialogsKt.showDialogLoading();
        }

    }


    private void getGenreDetail(){

        String endpoint = getIntent().getStringExtra("endpoint");
        String endpointD = getIntent().getStringExtra("endpointD");

        if (endpoint != null && !endpoint.isEmpty()) {
            url = Endpoints.KOMIK_LIST_DETAIL_GENRE+getIntent().getStringExtra("endpoint")+ "page/"+pageCount;

        } else if (endpointD != null && !endpointD.isEmpty()) {
            url = Endpoints.KOMIK_LIST_DETAIL_GENRE_FROM_DETAIL+getIntent().getStringExtra("endpointD")+ "/page/"+pageCount;
        }

        Networking.HttpsRequest(url, this, new Networking.Response() {
            @Override
            public void onHttpsResponse(JSONObject jsonObject) {
                getBinding().toolbar.setTitle(getIntent().getStringExtra("name"));

                genreList.clear();
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = jsonArray.getJSONObject(i);

                        ModelBaseKomik < String > modelGenre = new ModelBaseKomik<>();
                        modelGenre.setTitle(data.getString("name"));
                        modelGenre.setThumbnail(data.getString("thumb"));
                        modelGenre.setType(" ");

                        JSONObject link = data.getJSONObject("link");
                        modelGenre.setEndPoint(link.getString("endpoint"));
                        genreList.add(modelGenre);

                    }
                    adapterGenreDetail(genreList);
                } catch (JSONException e) {
                    e.printStackTrace();
                    DialogsKt.dismissDialogLoading();
                }

            }

            @Override
            public void onHttpsError(ANError anError) {
                DialogsKt.dismissDialogLoading();
                Toast.makeText(GenreDetail.this, "Error saat mencoba mengambil data disebabkan : "+anError.getErrorDetail(), Toast.LENGTH_SHORT).show();

                getBinding().parentLottie.setVisibility(View.VISIBLE);
                getBinding().rvGenreDetail.setVisibility(View.GONE);
                getBinding().parentButton.setVisibility(View.GONE);

            }
        });
    }

    private void adapterGenreDetail(List<ModelBaseKomik <String> > genreList) {

        AdapterKomik adapterKomik = new AdapterKomik(this, genreList);
        getBinding().rvGenreDetail.setLayoutManager(new GridLayoutManager(this, 3));
        getBinding().rvGenreDetail.setAdapter(adapterKomik);
        DialogsKt.dismissDialogLoading();

    }


    private void navigateToAnotherPage(){

        getBinding().btnGenreSebelumnya.setOnClickListener(v-> {

            if (pageCount == 1 ){
                getBinding().btnGenreSebelumnya.setEnabled(false);

            }else {
                pageCount--;
                DialogsKt.showDialogLoading();
                getBinding().btnGenreSebelumnya.setEnabled(true);
                getGenreDetail();
            }
        });

        getBinding().btnGenreSelanjutnya.setOnClickListener(v->{

            pageCount++;
            getBinding().btnGenreSebelumnya.setEnabled(true);
            DialogsKt.showDialogLoading();
            getGenreDetail();
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (isTransition){
            Animatoo.INSTANCE.animateSwipeRight(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        NoInternet.Companion.checkInternet(getLifecycle(), this);
    }
}
