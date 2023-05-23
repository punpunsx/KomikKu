package com.komikatow.komiku.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.komikatow.komiku.MainActivity;
import com.komikatow.komiku.R;
import com.komikatow.komiku.adapter.AdapterChapter;
import com.komikatow.komiku.adapter.AdapterGenre;
import com.komikatow.komiku.databinding.ActivityDetailBinding;
import com.komikatow.komiku.model.ModelChapter;
import com.komikatow.komiku.model.ModelGenre;
import com.komikatow.komiku.utils.DialogsKt;
import com.komikatow.komiku.utils.Endpoints;
import com.komikatow.komiku.utils.ItemRecyclerClick;
import com.komikatow.komiku.utils.Networking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class DetailActivity extends BaseActivity <ActivityDetailBinding> implements ItemRecyclerClick {
    private final List < ModelGenre < String > > listGenre = new ArrayList<>();
    private final List < ModelChapter < String > > listChapter = new ArrayList<>();


    //variabel untuk diset ke db
    private String titleKomik;
    private String thumbnail;
    private String waktu;
    private String endpointDetail;


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

    }

    private void getDetailKomik(){

        Networking.HttpsRequest(Endpoints.KOMIK_DETAIL + getIntent().getStringExtra("endpoint"), this, new Networking.Response() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onHttpsResponse(JSONObject jsonObject) {

                try {

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
                    getBinding().tvRating.setRating(Float.parseFloat(jsonObject.getString("score").trim()));

                    if (getIntent().getStringExtra("type")!= null || getIntent().getStringExtra("type") != " "){
                        getBinding().tvType.setText("Type : "+getIntent().getStringExtra("type"));
                    } else{
                        getBinding().tvType.setVisibility(View.GONE);
                    }

                    getResponseGenre(jsonObject);
                    getResponseChapters(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                    DialogsKt.dialogProgrees.dismiss();
                }
            }

            @Override
            public void onHttpsError(ANError anError) {

                Toast.makeText(DetailActivity.this, "Error at : "+anError.getErrorBody(), Toast.LENGTH_SHORT).show();
                DialogsKt.dialogProgrees.dismiss();
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

            startActivity(intent);

        });
        getBinding().rvGenre.setLayoutManager(new GridLayoutManager(this, 4));
        getBinding().rvGenre.setAdapter(adapterGenre);

        DialogsKt.dialogProgressCustum.dismiss();

    }

    private void getResponseChapters(JSONObject jsonObject) throws JSONException{

        JSONArray jsonArray = jsonObject.getJSONArray("chapters");
        for (int i = 0; i < jsonArray.length(); i++) {

            ModelChapter < String > modelChapter = new ModelChapter<>();
            JSONObject jsonCh = jsonArray.getJSONObject(i);
            modelChapter.setNemeCh(jsonCh.getString("name"));

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
    private void adapterChapter(List<ModelChapter<String>> listChapter) {

        AdapterChapter adapterChapter = new AdapterChapter(this, this, listChapter,this);
        getBinding().rvChapter.setLayoutManager(new GridLayoutManager(this, 4));
        getBinding().rvChapter.setAdapter(adapterChapter);

        getBinding().tvTotalCh.setText("Total Chapter : "+ adapterChapter.getItemCount());
    }

    @Override
    public void onClickListener(int pos) {

        Intent intent = new Intent(getApplicationContext(), BacaActivitty.class);
        intent.putExtra("endpoint",listChapter.get(pos).getEndPointCh());
        intent.putExtra("number", listChapter.get(pos).getNemeCh());

        startActivity(intent);

    }
}
