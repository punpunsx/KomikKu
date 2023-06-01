package com.komikatow.komiku;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;

import com.androidnetworking.error.ANError;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.komikatow.komiku.adapter.AdapterKomik;
import com.komikatow.komiku.databinding.ActivityMainBinding;
import com.komikatow.komiku.model.ModelBaseKomik;
import com.komikatow.komiku.ui.activityes.BaseActivity;
import com.komikatow.komiku.ui.activityes.backup.MainBackupActivity;
import com.komikatow.komiku.ui.fragments.FragmentFavorite;
import com.komikatow.komiku.ui.fragments.FragmentHome;
import com.komikatow.komiku.ui.fragments.FragmentRiwayat;
import com.komikatow.komiku.ui.fragments.FragmentSetting;
import com.komikatow.komiku.utils.Endpoints;
import com.komikatow.komiku.utils.Networking;
import com.komikatow.komiku.utils.NoInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public final class MainActivity extends BaseActivity <ActivityMainBinding> {
    private boolean isTwoClick = false;
    public static volatile String hasilDate;
    private final List<ModelBaseKomik <String> > listSearch = new ArrayList<>();
    @Override
    protected ActivityMainBinding createBinding(LayoutInflater layoutInflater) {
        return ActivityMainBinding.inflate(layoutInflater);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isTransition = sharedPreferences.getBoolean("animasiTransisi", false);
        String defValue = sharedPreferences.getString("listServer", "Default Server");

        getTextInSearchBar();
        if (defValue.equals("Default Server")){

            setFrament(new FragmentHome(),"home_tag");
            getBinding().bottomNav.setSelectedItemId(R.id.action_home);
            onItemMenuSelected();

        }else if (defValue.equals("Backup Server")){
            if (isTransition){

                startActivity(new Intent(getApplicationContext(), MainBackupActivity.class));
                Animatoo.INSTANCE.animateZoom(this);
                finish();

            }else {
                startActivity(new Intent(getApplicationContext(), MainBackupActivity.class));
                finish();
            }
        }
    }

    public static void getTimeInLocale(){

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        final int date = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);

        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        hasilDate = date + "-" + month + "-"+year + " : "+hour + ":" + minute ;

    }

    private void onItemMenuSelected(){
        getBinding().bottomNav.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.action_home){
                setFrament(new FragmentHome(), "home_tag");
                refleshWithNewLangague();

            } else if (item.getItemId() ==  R.id.action_fav) {
                setFrament(new FragmentFavorite(), null);
                refleshWithNewLangague();

            } else if (item.getItemId() ==  R.id.action_riwayat) {
                setFrament(new FragmentRiwayat(), null);
                refleshWithNewLangague();

            } else if (item.getItemId() ==  R.id.action_setting) {
                setFrament(new FragmentSetting(), null);
                refleshWithNewLangague();

            }else {
                setFrament(new FragmentHome(), "home_tag");
                refleshWithNewLangague();
            }

            return true;
        });
    }

    private void setFrament(Fragment frament, @Nullable String tag){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment existFragment = fragmentManager.findFragmentByTag(tag);

        if (existFragment != null){
            fragmentTransaction.show(existFragment);
        }else {
            fragmentTransaction.add(R.id.containerMain, frament, tag);
        }

        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null){
            for (Fragment frag : fragments){
                if (frag != existFragment){
                    fragmentTransaction.hide(frag);
                }
            }
        }

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }

    private void refleshWithNewLangague(){

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean bahasaSaatIni = sharedPreferences.getBoolean("bahasa", true);

        if (bahasaSaatIni){

            getBinding().bottomNav.getMenu().findItem(R.id.action_home).setTitle("Home");
            getBinding().bottomNav.getMenu().findItem(R.id.action_fav).setTitle("Favorite");
            getBinding().bottomNav.getMenu().findItem(R.id.action_riwayat).setTitle("History");
            getBinding().bottomNav.getMenu().findItem(R.id.action_setting).setTitle("Settings");

        }else {
            getBinding().bottomNav.getMenu().findItem(R.id.action_home).setTitle("Rumah");
            getBinding().bottomNav.getMenu().findItem(R.id.action_fav).setTitle("Favorite");
            getBinding().bottomNav.getMenu().findItem(R.id.action_riwayat).setTitle("Riwayat");
            getBinding().bottomNav.getMenu().findItem(R.id.action_setting).setTitle("Pengaturan");
        }

    }

    private void getTextInSearchBar(){

        getBinding().searchView.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                getResponseSearch(getBinding().searchView.getText().toString());
            }
            return true;
        });

        getBinding().searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getBinding().searchProgress.setVisibility(View.VISIBLE);
                getResponseSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getResponseSearch(String query){

        Networking.HttpsRequest(Endpoints.KOMIK_ALL_SEARCH + query, this, new Networking.Response() {
            @Override
            public void onHttpsResponse(JSONObject jsonObject) {
                listSearch.clear();

                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("mangas");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject mangas = jsonArray.getJSONObject(i);
                        JSONObject link = mangas.getJSONObject("link");
                        ModelBaseKomik <String> modelBaseKomik = new ModelBaseKomik<>();

                        modelBaseKomik.setTitle(mangas.getString("name"));
                        modelBaseKomik.setThumbnail(mangas.getString("thumb"));
                        modelBaseKomik.setType(" ");
                        modelBaseKomik.setEndPoint(link.getString("endpoint"));
                        listSearch.add(modelBaseKomik);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterSearch(listSearch);
            }

            @Override
            public void onHttpsError(ANError anError) {
                anError.printStackTrace();
                getBinding().searchProgress.setVisibility(View.GONE);
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void adapterSearch(List<ModelBaseKomik<String>> listSearch) {

        AdapterKomik adapterKomik = new AdapterKomik(this, listSearch);
        getBinding().rvResultSearch.setLayoutManager(new GridLayoutManager(this, 3));
        getBinding().rvResultSearch.setAdapter(adapterKomik);
        adapterKomik.notifyDataSetChanged();

        getBinding().searchProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {

        if (isTwoClick){
            super.onBackPressed();
            return;
        }

        isTwoClick = true;
        Toast.makeText(this, "Tekan tombol back 1 kali lagi untuk keluar", Toast.LENGTH_SHORT).show();

        Handler handler = new Handler();
        handler.postDelayed(() -> isTwoClick = false, 5000);

    }

    @Override
    protected void onResume() {
        super.onResume();

        NoInternet.Companion.checkInternet(getLifecycle(), this);
        Networking.getUpdate(this, this);
    }
}