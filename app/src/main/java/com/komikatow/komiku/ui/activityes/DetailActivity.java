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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidnetworking.error.ANError;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.komikatow.komiku.MainActivity;
import com.komikatow.komiku.R;
import com.komikatow.komiku.adapter.AdapterChapter;
import com.komikatow.komiku.adapter.AdapterGenre;
import com.komikatow.komiku.databinding.ActivityDetailBinding;
import com.komikatow.komiku.model.ModelGenre;
import com.komikatow.komiku.room.dbApp.AdvanceDbApp;
import com.komikatow.komiku.room.dbApp.FavoriteDbApp;
import com.komikatow.komiku.room.dbApp.HistoryDbApp;
import com.komikatow.komiku.room.enity.AdvanceSizeEnity;
import com.komikatow.komiku.room.enity.FavoriteEnity;
import com.komikatow.komiku.room.enity.ModelChapter;
import com.komikatow.komiku.utils.DialogsKt;
import com.komikatow.komiku.utils.Endpoints;
import com.komikatow.komiku.utils.ItemRecyclerClick;
import com.komikatow.komiku.utils.Networking;
import com.komikatow.komiku.utils.NoInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class DetailActivity extends BaseActivity <ActivityDetailBinding> implements ItemRecyclerClick {
    private final List < ModelGenre < String > > listGenre = new ArrayList<>();
    private final List < ModelChapter > listChapter = new ArrayList<>();

    //variabel untuk diset ke db
    private String titleKomik;
    private String thumbnail;
    private String chapterName;
    private String waktu;
    private String endpointDetail;

    //cek apakah state dari switchPreference sedang on / off
    private boolean transitionStatus;
    private boolean animasiGambar;
    private boolean getBahasa;
    private boolean modeCh;


    @Override
    protected ActivityDetailBinding createBinding(LayoutInflater layoutInflater) {
        return ActivityDetailBinding.inflate(layoutInflater);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DialogsKt.setDialogLoadingCustum(this,  "Sedang Mengambil Data...");
        DialogsKt.dialogProgressCustum.setCancelable(false);
        DialogsKt.dialogProgressCustum.show();

        getDetailKomik();
        MainActivity.getTimeInLocale();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        transitionStatus = sharedPreferences.getBoolean("animasiTransisi", false);
        animasiGambar = sharedPreferences.getBoolean("animasiGambar", true);
        getBahasa = sharedPreferences.getBoolean("bahasa", true);
        modeCh = sharedPreferences.getBoolean("modeCh", true);


        getBinding().toolbar.setNavigationOnClickListener(v->{

            if (transitionStatus){
                finish();
                Animatoo.INSTANCE.animateSwipeRight(this);

            }else {
                finish();
            }

        });
        getDbAdvance();
    }

    private void getDbAdvance(){
        new Thread(() -> {

            AdvanceDbApp database = AdvanceDbApp.getInstance(getApplicationContext());
            List<AdvanceSizeEnity> getALl = database.dao().getAll();

            if (!getALl.isEmpty()){
                CollapsingToolbarLayout.LayoutParams params = (CollapsingToolbarLayout.LayoutParams) getBinding().detailImage.getLayoutParams();
                params.height = Integer.parseInt(getALl.get(0).getDetail());
            }

        }).start();
    }

    private void getDetailKomik(){

        Networking.HttpsRequest(Endpoints.KOMIK_DETAIL + getIntent().getStringExtra("endpoint"), this, new Networking.Response() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onHttpsResponse(JSONObject jsonObject) {

                try {

                    final String type = getIntent().getStringExtra("type");
                    titleKomik = jsonObject.getString("title").replace("\n", " ").trim();
                    thumbnail = jsonObject.getString("thumb");
                    endpointDetail = getIntent().getStringExtra("endpoint");
                    waktu = MainActivity.hasilDate;

                    Glide.with(getApplicationContext())
                            .load(thumbnail)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.color.ErrorLoadImage)
                            .placeholder(R.color.PlaceHolderImage)
                            .into(getBinding().detailImage);

                    getBinding().colapse.setTitle(titleKomik);
                    getBinding().tvStatus.setText("Status : "+jsonObject.getString("status").trim());
                    getBinding().tvAuthor.setText("Author : "+jsonObject.getString("author").trim());
                    getBinding().tvIlustrator.setText("Illustrator : "+jsonObject.getString("illustator").trim());
                    getBinding().tvRating.setRating(Float.parseFloat(jsonObject.getString("score").trim()) / 2);

                    if (type != null && !type.isEmpty() && type != " "){
                        getBinding().tvType.setVisibility(View.VISIBLE);
                        getBinding().tvType.setText("Type : "+getIntent().getStringExtra("type"));

                    } else{
                        getBinding().tvType.setVisibility(View.GONE);
                    }

                    getResponseGenre(jsonObject);
                    getResponseChapters(jsonObject);

                    validasiDataFromFavoriteDatabase();

                } catch (JSONException e) {
                    e.printStackTrace();
                    DialogsKt.dismissDialogLoading();
                }
            }

            @Override
            public void onHttpsError(ANError anError) {

                DialogsKt.dialogProgressCustum.dismiss();
                View view  =findViewById(android.R.id.content);
                Snackbar snackbar = Snackbar.make(getApplicationContext(),view, "Terjadi error saat menampilkan data ",Snackbar.LENGTH_SHORT);
                snackbar.setAction("Tutup", v -> snackbar.dismiss());
                snackbar.show();

                getBinding().parentLottie.setVisibility(View.VISIBLE);
                getBinding().nested.setVisibility(View.GONE);

            }
        });
    }

    private void getResponseGenre(JSONObject jsonObject) throws JSONException {

        JSONArray jsonArray = jsonObject.getJSONArray("genres");
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonGenre = jsonArray.getJSONObject(i);
            ModelGenre<String> modelGenre = new ModelGenre<>();

            modelGenre.setGenre(jsonGenre.getString("name"));
            JSONObject jsonLink = jsonGenre.getJSONObject("link");
            modelGenre.setEndpointgenre(jsonLink.getString("endpoint"));
            listGenre.add(modelGenre);

        }
        adapterGenre(listGenre);
    }

    private void adapterGenre(List < ModelGenre < String > > listGenre) {

        AdapterGenre adapterGenre = new AdapterGenre(this, listGenre, pos -> {

            Intent intent = new Intent(getApplicationContext(), GenreDetail.class);
            intent.putExtra("name",listGenre.get(pos).getGenre());
            intent.putExtra("endpointD",listGenre.get(pos).getEndpointgenre());

            if (transitionStatus){
                startActivity(intent);
                Animatoo.INSTANCE.animateZoom(this);

            }else {
                startActivity(intent);
            }

        });
        getBinding().rvGenre.setLayoutManager(new GridLayoutManager(this, 4));
        getBinding().rvGenre.setAdapter(adapterGenre);

        DialogsKt.dialogProgressCustum.dismiss();

    }

    private void getResponseChapters(JSONObject jsonObject) throws JSONException{

        JSONArray jsonArray = jsonObject.getJSONArray("chapters");
        for (int i = 0; i < jsonArray.length(); i++) {

            ModelChapter modelChapter = new ModelChapter();
            JSONObject jsonCh = jsonArray.getJSONObject(i);
            modelChapter.setNemeCh(jsonCh.getString("name").replace("\n", " "));

            JSONObject link = jsonCh.getJSONObject("link");
            modelChapter.setEndPointCh(link.getString("endpoint"));

            //Diset ke model db history
            modelChapter.setDate(waktu);
            modelChapter.setNameKomik(titleKomik);
            modelChapter.setThumbnail(thumbnail);
            modelChapter.setEndPointDetail(endpointDetail);

            listChapter.add(modelChapter);
        }
        adapterChapter(listChapter);
    }

    @SuppressLint("SetTextI18n")
    private void adapterChapter(List<ModelChapter> listChapter) {

        AdapterChapter adapterChapter = new AdapterChapter(this, this, listChapter,this);
        getBinding().rvChapter.setAdapter(adapterChapter);

        if (getBahasa){
            getBinding().tvTotalCh.setText("Total Chapters : "+ adapterChapter.getItemCount());
        }else {
            getBinding().tvTotalCh.setText("Jumlah Chapter : "+ adapterChapter.getItemCount());
        }


        if (modeCh){
            getBinding().rvChapter.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));

        }else {
            getBinding().rvChapter.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        }

    }

    //Ketika item dari chapter di klik
    @Override
    public void onClickListener(int pos) {

        chapterName = listChapter.get(pos).getNemeCh();
        validasiDataFromRiwayatDatabase(pos);
    }

    //Melakukan pengecekan apakah data dari komik yg dihalaman ini sudah ada / belum pada database room
    private void validasiDataFromRiwayatDatabase(int position){

        HistoryDbApp database = HistoryDbApp.getInstance(getApplicationContext());
        ModelChapter data = new ModelChapter(endpointDetail, waktu, titleKomik, chapterName, thumbnail);

        new Thread(() -> {

            //Data udah ada
            if (database.dao().checkIfExist(endpointDetail)){

                database.dao().delete(data);
                database.dao().insert(data);
                runOnUiThread(()-> toReadActivity(position));

            }else {
                //Data belum ada
                database.dao().insert(data);
                runOnUiThread(()-> toReadActivity(position));

            }
        }).start();
    }

    private void toReadActivity(int pos){

        Intent intent = new Intent(getApplicationContext(), BacaActivitty.class);
        intent.putExtra("endpoint",listChapter.get(pos).getEndPointCh());
        intent.putExtra("number", listChapter.get(pos).getNemeCh());

        if (transitionStatus){
            startActivity(intent);
            Animatoo.INSTANCE.animateZoom(this);

        }else {
            startActivity(intent);
        }

    }


    //Melakukan pengecekan apakah data dari komik yg dihalaman ini sudah ada / belum pada database room
    private void validasiDataFromFavoriteDatabase(){


        FavoriteEnity data = new FavoriteEnity(endpointDetail, titleKomik, thumbnail);
        FavoriteDbApp database = FavoriteDbApp.getInstance(this);


        new Thread(() -> {

            if (database.dao().checkIfExist(endpointDetail)){
                //Data sudah ada
                runOnUiThread(() -> getBinding().fabAdd.setEnabled(false));

            }else {
                //Data belum ada
                runOnUiThread(() -> getBinding().fabAdd.setEnabled(true));
                getBinding().fabAdd.setOnClickListener(v-> {
                    new Thread(() -> database.dao().insertData(data)).start();
                    runOnUiThread(() -> {
                        getBinding().fabAdd.setEnabled(false);

                        if (getBahasa){
                            Toast.makeText(DetailActivity.this, "Successfully added data to favorites", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(DetailActivity.this, "Berhasil menambahkan data ke favorite", Toast.LENGTH_SHORT).show();
                        }


                    });
                });

            }


        }).start();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (endpointDetail != null && !endpointDetail.isEmpty()){
            getBinding().fabAdd.setEnabled(true);
        }else{
            getBinding().fabAdd.setEnabled(false);
        }

        if (animasiGambar){
            getBinding().detailImage.resume();
        }else {
            getBinding().detailImage.pause();
        }

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
