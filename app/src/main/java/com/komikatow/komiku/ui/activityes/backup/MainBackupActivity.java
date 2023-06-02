package com.komikatow.komiku.ui.activityes.backup;

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
import com.komikatow.komiku.MainActivity;
import com.komikatow.komiku.R;
import com.komikatow.komiku.adapter.AdapterKomik;
import com.komikatow.komiku.databinding.ActivityMainBinding;
import com.komikatow.komiku.model.ModelBaseKomik;
import com.komikatow.komiku.ui.activityes.BaseActivity;
import com.komikatow.komiku.ui.fragments.FragmentSetting;
import com.komikatow.komiku.ui.fragments.backup.BackUpFavoriteFragment;
import com.komikatow.komiku.ui.fragments.backup.BackUpHomeFragment;
import com.komikatow.komiku.ui.fragments.backup.BackUpRiwayatFragment;
import com.komikatow.komiku.utils.DialogsKt;
import com.komikatow.komiku.utils.Endpoints;
import com.komikatow.komiku.utils.Networking;
import com.komikatow.komiku.utils.OnDialogListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainBackupActivity extends BaseActivity<ActivityMainBinding> {
    private boolean isTwoClick = false;
    private final List<ModelBaseKomik<String>> listSearch = new ArrayList<>();
    @Override
    protected ActivityMainBinding createBinding(LayoutInflater layoutInflater) {
        return ActivityMainBinding.inflate(layoutInflater);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DialogsKt.setAlertDialog(this, "Alert", "Ini adalah server Backup digunakan jika server utama error\nJika ingin kembali ke server utama silahkan ketuk tombol back 2x", false, new OnDialogListener() {
            @Override
            public void onOkeButton() {

            }

            @Override
            public void onCencleButton() {

            }
        });
        DialogsKt.showAlertDialog();
        setFrament(new BackUpHomeFragment(), "backup_home");
        onItemMenuSelected();
        getTextInSearchBar();
    }


    private void onItemMenuSelected() {

        getBinding().bottomNav.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.action_home){
                setFrament(new BackUpHomeFragment(), "backup_home");
                refleshWithNewLangague();

            } else if (item.getItemId() == R.id.action_fav) {
                setFrament(new BackUpFavoriteFragment(), null);
                refleshWithNewLangague();

            } else if (item.getItemId() == R.id.action_riwayat) {
                setFrament(new BackUpRiwayatFragment(), null);
                refleshWithNewLangague();

            }else if (item.getItemId() == R.id.action_setting){
                setFrament(new FragmentSetting(), null);
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

        Networking.HttpsRequest(Endpoints.KOMIK_SEARCH_BACKUP + query, this, new Networking.Response() {
            @Override
            public void onHttpsResponse(JSONObject jsonObject) {
                listSearch.clear();

                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject mangas = jsonArray.getJSONObject(i);
                        ModelBaseKomik <String> modelBaseKomik = new ModelBaseKomik<>();

                        modelBaseKomik.setTitle(mangas.getString("title"));
                        modelBaseKomik.setThumbnail(mangas.getString("image"));
                        modelBaseKomik.setType(mangas.getString("type"));
                        modelBaseKomik.setEndPoint(mangas.getString("endpoint"));
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
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit().putString("listServer", "Default Server");
            editor.apply();

            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        isTwoClick = true;
        Toast.makeText(this, "Tekan tombol back 1 kali lagi untuk Kembali ke server utama", Toast.LENGTH_SHORT).show();

        Handler handler = new Handler();
        handler.postDelayed(() -> isTwoClick = false, 5000);
    }


}
