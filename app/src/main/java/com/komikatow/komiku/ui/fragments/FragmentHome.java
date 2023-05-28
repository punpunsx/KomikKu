package com.komikatow.komiku.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidnetworking.error.ANError;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.komikatow.komiku.adapter.AdapterGenre;
import com.komikatow.komiku.adapter.AdapterKomik;
import com.komikatow.komiku.adapter.AdapterKomikJepang;
import com.komikatow.komiku.databinding.FragmentHomeBinding;
import com.komikatow.komiku.model.ModelBaseKomik;
import com.komikatow.komiku.model.ModelGenre;
import com.komikatow.komiku.ui.activityes.DetailActivity;
import com.komikatow.komiku.ui.activityes.GenreDetail;
import com.komikatow.komiku.ui.activityes.SearchActivity;
import com.komikatow.komiku.utils.DialogsKt;
import com.komikatow.komiku.utils.Endpoints;
import com.komikatow.komiku.utils.ItemRecyclerClick;
import com.komikatow.komiku.utils.Networking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// maaf jika di file / fragment ini banyak code duplication.
public final class FragmentHome extends BaseFragment <FragmentHomeBinding> implements ItemRecyclerClick {
    private final List < ModelBaseKomik < String > > listKomikJepang = new ArrayList<>();
    private final List < ModelBaseKomik < String > > listRelease = new ArrayList<>();
    private final List < ModelBaseKomik < String > > listKomikKorea = new ArrayList<>();
    private final List < ModelBaseKomik < String > > listKomikChina = new ArrayList<>();
    private final List < ModelGenre < String > > listGenre = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    @Override
    protected FragmentHomeBinding createBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentHomeBinding.inflate(inflater, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        getBinding().btnSarch.setOnClickListener(v-> {
            startActivity(new Intent(getContext(), SearchActivity.class));
            if (sharedPreferences.getBoolean("animasiTransisi", false)){
                Animatoo.INSTANCE.animateZoom(requireContext());
            }
        });

        if (sharedPreferences.getBoolean("bahasa", true)){
            getBinding().txtKomikJepangId.setText("Japanese comics");
            getBinding().txtGenreId.setText("List Genre");
            getBinding().txtReleaseId.setText("Latest comic updates");
            getBinding().txtKomikKoreaId.setText("Korean comics");
            getBinding().txtKomikChinaId.setText("Chinese comics");

            DialogsKt.setDialogLoading(requireContext(), "Loading...", "Please Wait", false);
            DialogsKt.showDialogLoading();

        }else {
            DialogsKt.setDialogLoading(requireContext(), "Loading...", "Mohon tunggu sebentar", false);
            DialogsKt.showDialogLoading();
        }

        getResponseKomikJepang();
        getResponseGenreList();
        getResponseRelease();
        getResponseKomikKorea();
        getResponseKomikChina();

    }


    private void getResponseKomikJepang(){

        Networking.HttpsRequest(Endpoints.KOMIK_TYPE_MANGA, getActivity(), new Networking.Response() {
            @Override
            public void onHttpsResponse(JSONObject jsonObject) {

                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("mangas");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        ModelBaseKomik<String> modelBaseKomik = new ModelBaseKomik<>();
                        JSONObject detailObject = jsonArray.getJSONObject(i);

                        modelBaseKomik.setTitle(detailObject.getString("name"));
                        modelBaseKomik.setThumbnail(detailObject.getString("thumb"));
                        modelBaseKomik.setType("Manga");

                        JSONObject detailLink = detailObject.getJSONObject("link");
                        modelBaseKomik.setEndPoint(detailLink.getString("endpoint"));
                        listKomikJepang.add(modelBaseKomik);

                    }
                    if (isAdded()){
                        adapterKomikJepang(listKomikJepang);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onHttpsError(ANError anError) {

                Toast.makeText(getContext(), "Error on : "+anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void adapterKomikJepang(List < ModelBaseKomik <String> > listKomikJepang) {

        AdapterKomikJepang adapterKomikJepang = new AdapterKomikJepang(getContext(), listKomikJepang, this);
        getBinding().rvKomikJepangSlider.setAdapter(adapterKomikJepang);

    }

    private void getResponseGenreList(){

        Networking.HttpsRequest(Endpoints.KOMIK_LIST_GENRE, getActivity(), new Networking.Response() {
            @Override
            public void onHttpsResponse(JSONObject jsonObject) {

                try {

                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonListGenre = jsonArray.getJSONObject(i);
                        ModelGenre<String> modelGenre = new ModelGenre<>();

                        modelGenre.setGenre(jsonListGenre.getString("name"));
                        JSONObject link = jsonListGenre.getJSONObject("link");
                        modelGenre.setEndpointgenre(link.getString("endpoint"));
                        listGenre.add(modelGenre);
                    }
                    if (isAdded()){
                        adapterListGenre(listGenre);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onHttpsError(ANError anError) {
                Toast.makeText(getContext(), "Error on : "+anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void adapterListGenre(List<ModelGenre<String>> listGenre) {

        AdapterGenre adapterGenre = new AdapterGenre(getContext(), listGenre, pos -> {

            Intent intent = new Intent(getContext(), GenreDetail.class);
            intent.putExtra("name",listGenre.get(pos).getGenre());
            intent.putExtra("endpoint",listGenre.get(pos).getEndpointgenre());

            if (sharedPreferences.getBoolean("animasiTransisi", false)){
                startActivity(intent);
                Animatoo.INSTANCE.animateZoom(requireContext());

            }else {
                startActivity(intent);
            }

        });
        getBinding().rvListGenre.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        getBinding().rvListGenre.setAdapter(adapterGenre);

    }


    private void getResponseRelease(){

        Networking.HttpsRequest(Endpoints.KOMIK_TERBARU, getActivity(), new Networking.Response() {
            @Override
            public void onHttpsResponse(JSONObject jsonObject) {

                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("mangas");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        ModelBaseKomik<String> modelBaseKomik = new ModelBaseKomik<>();
                        JSONObject detailObject = jsonArray.getJSONObject(i);

                        modelBaseKomik.setTitle(detailObject.getString("name"));
                        modelBaseKomik.setThumbnail(detailObject.getString("thumb"));
                        modelBaseKomik.setType(" ");

                        JSONObject detailLink = detailObject.getJSONObject("link");
                        modelBaseKomik.setEndPoint(detailLink.getString("endpoint"));
                        listRelease.add(modelBaseKomik);

                    }
                    if (isAdded()){
                        adapterRelease(listRelease);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onHttpsError(ANError anError) {

                Toast.makeText(getContext(), "Error on : "+anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void adapterRelease(List < ModelBaseKomik <String> > listRelease) {

        AdapterKomik adapterKomik = new AdapterKomik(getContext(), listRelease);
        getBinding().rvRelease.setLayoutManager(new GridLayoutManager(getContext(), 3));
        getBinding().rvRelease.setAdapter(adapterKomik);

    }


    private void getResponseKomikKorea(){

        Networking.HttpsRequest(Endpoints.KOMIK_TYPE_MANHWA, getActivity(), new Networking.Response() {
            @Override
            public void onHttpsResponse(JSONObject jsonObject) {

                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("mangas");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        ModelBaseKomik<String> modelBaseKomik = new ModelBaseKomik<>();
                        JSONObject detailObject = jsonArray.getJSONObject(i);

                        modelBaseKomik.setTitle(detailObject.getString("name"));
                        modelBaseKomik.setThumbnail(detailObject.getString("thumb"));
                        modelBaseKomik.setType("Manhwa");

                        JSONObject detailLink = detailObject.getJSONObject("link");
                        modelBaseKomik.setEndPoint(detailLink.getString("endpoint"));
                        listKomikKorea.add(modelBaseKomik);

                    }
                    if (isAdded()){
                        adapterKomikKorea(listKomikKorea);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onHttpsError(ANError anError) {

                Toast.makeText(getContext(), "Error on : "+anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void adapterKomikKorea(List<ModelBaseKomik<String>> listKomikKorea) {

        AdapterKomik adapterKomik = new AdapterKomik(getContext(), listKomikKorea);
        getBinding().rvKomikKorea.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        getBinding().rvKomikKorea.setAdapter(adapterKomik);

    }

    private void getResponseKomikChina(){

        Networking.HttpsRequest(Endpoints.KOMIK_TYPE_MANHUA, getActivity(), new Networking.Response() {
            @Override
            public void onHttpsResponse(JSONObject jsonObject) {

                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("mangas");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        ModelBaseKomik<String> modelBaseKomik = new ModelBaseKomik<>();
                        JSONObject detailObject = jsonArray.getJSONObject(i);

                        modelBaseKomik.setTitle(detailObject.getString("name"));
                        modelBaseKomik.setThumbnail(detailObject.getString("thumb"));
                        modelBaseKomik.setType("Manhua");

                        JSONObject detailLink = detailObject.getJSONObject("link");
                        modelBaseKomik.setEndPoint(detailLink.getString("endpoint"));
                        listKomikChina.add(modelBaseKomik);

                    }
                    if (isAdded()){
                        adapterKomikChina(listKomikChina);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    DialogsKt.dismissDialogLoading();
                }

            }

            @Override
            public void onHttpsError(ANError anError) {

                Toast.makeText(getContext(), "Error on : "+anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                DialogsKt.dismissDialogLoading();
            }
        });
    }

    private void adapterKomikChina(List<ModelBaseKomik<String>> listKomikChina) {

        AdapterKomik adapterKomik = new AdapterKomik(getContext(), listKomikChina);
        getBinding().rvKomikChina.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        getBinding().rvKomikChina.setAdapter(adapterKomik);

        DialogsKt.dismissDialogLoading();
    }

    @Override
    public void onClickListener(int pos) {

        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("endpoint", listKomikJepang.get(pos).getEndPoint());
        intent.putExtra("type", listKomikJepang.get(pos).getType());


        boolean transitionStatus = sharedPreferences.getBoolean("animasiTransisi", false);

        if (transitionStatus){
            startActivity(intent);
            Animatoo.INSTANCE.animateZoom(requireContext());
        }else {
            startActivity(intent);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        DialogsKt.dismissDialogLoading();
    }
}
