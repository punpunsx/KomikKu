package com.komikatow.komiku.ui.activityes.backup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import com.komikatow.komiku.MainActivity;
import com.komikatow.komiku.R;
import com.komikatow.komiku.databinding.ActivityMainBinding;
import com.komikatow.komiku.ui.activityes.BaseActivity;
import com.komikatow.komiku.ui.fragments.backup.BackUpHomeFragment;
import com.komikatow.komiku.utils.DialogsKt;
import com.komikatow.komiku.utils.OnDialogListener;

import java.util.List;

public class MainBackupActivity extends BaseActivity<ActivityMainBinding> {
    private boolean isTwoClick = false;

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
    }


    private void onItemMenuSelected() {

        getBinding().bottomNav.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.action_home){
                setFrament(new BackUpHomeFragment(), "backup_home");
                refleshWithNewLangague();

            } else if (item.getItemId() == R.id.action_fav) {
                refleshWithNewLangague();

            } else if (item.getItemId() == R.id.action_riwayat) {
                refleshWithNewLangague();

            }else if (item.getItemId() == R.id.action_setting){
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
