package com.komikatow.komiku;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.google.android.material.navigation.NavigationBarView;
import com.komikatow.komiku.databinding.ActivityMainBinding;
import com.komikatow.komiku.ui.activityes.BaseActivity;
import com.komikatow.komiku.ui.fragments.FragmentFavorite;
import com.komikatow.komiku.ui.fragments.FragmentHome;
import com.komikatow.komiku.ui.fragments.FragmentRiwayat;
import com.komikatow.komiku.ui.fragments.FragmentSetting;
import com.komikatow.komiku.utils.Networking;
import com.komikatow.komiku.utils.NoInternet;

import java.util.Calendar;
import java.util.Locale;

public final class MainActivity extends BaseActivity <ActivityMainBinding> implements NavigationBarView.OnItemSelectedListener {
    private boolean isTwoClick = false;
    public static volatile String hasilDate;

    @Override
    protected ActivityMainBinding createBinding(LayoutInflater layoutInflater) {
        return ActivityMainBinding.inflate(layoutInflater);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFrament(new FragmentHome());
        getBinding().mainBottomBar.setSelectedItemId(R.id.action_home);
        onItemMenuSelected();
        Networking.getUpdate(this, this);

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
        getBinding().mainBottomBar.setOnItemSelectedListener(this);
    }

    private void setFrament(Fragment frament){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer ,frament)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_home){
            setFrament(new FragmentHome());
            refleshWithNewLangague();

        } else if (item.getItemId() ==  R.id.action_fav) {
            setFrament(new FragmentFavorite());
            refleshWithNewLangague();

        } else if (item.getItemId() ==  R.id.action_riwayat) {
            setFrament(new FragmentRiwayat());
            refleshWithNewLangague();

        } else if (item.getItemId() ==  R.id.action_setting) {
            setFrament(new FragmentSetting());
            refleshWithNewLangague();

        }else {
            setFrament(new FragmentHome());
            refleshWithNewLangague();
        }

        return true;
    }

    private void refleshWithNewLangague(){

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean bahasaSaatIni = sharedPreferences.getBoolean("bahasa", true);

        if (bahasaSaatIni){

            getBinding().mainBottomBar.getMenu().findItem(R.id.action_home).setTitle("Home");
            getBinding().mainBottomBar.getMenu().findItem(R.id.action_fav).setTitle("Favorite");
            getBinding().mainBottomBar.getMenu().findItem(R.id.action_riwayat).setTitle("History");
            getBinding().mainBottomBar.getMenu().findItem(R.id.action_setting).setTitle("Settings");

        }else {
            getBinding().mainBottomBar.getMenu().findItem(R.id.action_home).setTitle("Rumah");
            getBinding().mainBottomBar.getMenu().findItem(R.id.action_fav).setTitle("Favorite");
            getBinding().mainBottomBar.getMenu().findItem(R.id.action_riwayat).setTitle("Riwayat");
            getBinding().mainBottomBar.getMenu().findItem(R.id.action_setting).setTitle("Pengaturan");
        }

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
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        getBinding().mainBottomBar.setSelectedItemId(R.id.action_home);
        setFrament(new FragmentHome());

    }

    @Override
    protected void onResume() {
        super.onResume();

        NoInternet.Companion.checkInternet(getLifecycle(), this);
    }

}