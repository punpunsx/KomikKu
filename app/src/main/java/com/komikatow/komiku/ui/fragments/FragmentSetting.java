package com.komikatow.komiku.ui.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.komikatow.komiku.BuildConfig;
import com.komikatow.komiku.R;
import com.komikatow.komiku.ui.activityes.AdvanceActivity;
import com.komikatow.komiku.ui.activityes.backup.MainBackupActivity;

public final class FragmentSetting extends PreferenceFragmentCompat {
    private SwitchPreference mode, animasiDetail, animasiTransisi, cache, quality, modeCh;
    private ListPreference server;
    private Preference bahasa, bug, advance;
    private String modeServer;
    private boolean modeSummary;
    private boolean modeBahasa;
    private boolean modeAnmasiDetail;
    private boolean modeAnmasiTransisi;
    private boolean modeChOrientation;


    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.fragment_setting, rootKey);


        mode = findPreference("mode");
        animasiDetail = findPreference("animasiGambar");
        animasiTransisi = findPreference("animasiTransisi");
        cache = findPreference("cache");
        quality = findPreference("quality");
        advance = findPreference("advance");
        modeCh  = findPreference("modeCh");

        server =findPreference("listServer");
        bahasa = findPreference("bahasa");
        bug = findPreference("bug");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        modeSummary = sharedPreferences.getBoolean("mode", false);
        modeBahasa = sharedPreferences.getBoolean("bahasa", true);
        modeChOrientation = sharedPreferences.getBoolean("modeCh", true);
        modeAnmasiDetail  = sharedPreferences.getBoolean("animasiGambar", false);
        modeAnmasiTransisi = sharedPreferences.getBoolean("animasiTransisi", false);
        modeServer = sharedPreferences.getString("listServer", "Default Server");

        checkCurrentLanguage();
        onPreferenceClickOrStateChange();

    }

    private void onPreferenceClickOrStateChange(){

        bug.setOnPreferenceClickListener(preference -> {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://api.whatsapp.com/send/?phone=62895323021645&text&type=phone_number&app_absent=0"));
            startActivity(intent);

            return true;
        });


        final Preference size = findPreference("advance");
        assert size != null;
        size.setOnPreferenceClickListener(preference -> {
            startActivity(new Intent(getContext(), AdvanceActivity.class));

            return true;
        });

        server.setOnPreferenceChangeListener((preference, newValue) -> {
            modeServer = (String) newValue;

            if (modeServer.equals("Default Server")){

            } else if (modeServer.equals("Backup Server")) {

                if (modeAnmasiTransisi){

                    startActivity(new Intent(getContext(), MainBackupActivity.class));
                    Animatoo.INSTANCE.animateZoom(requireContext());
                    requireActivity().finish();

                }else {
                    startActivity(new Intent(getContext(), MainBackupActivity.class));
                    requireActivity().finish();
                }
            }

            return true;
        });


        bahasa.setOnPreferenceChangeListener((preference, newValue) -> {
            boolean newMode = (boolean)  newValue;

            if (newMode){
               ingrisLangague();

            } else {
                bahasaIndonesia();
            }

            return true;
        });
        mode.setOnPreferenceChangeListener((preference, newValue) -> {
            boolean newModeBaca = (boolean) newValue;

            if (modeBahasa){
                if (newModeBaca){
                    mode.setTitle(" Read mode");
                    mode.setSummary(" Current mode : Slider");

                }else {
                    mode.setTitle(" Read mode");
                    mode.setSummary(" Current mode : Scrool");
                }

            }else {
                if (!newModeBaca){
                    mode.setSummary(" Mode Saat ini : Slide");
                    mode.setTitle(" Mode Baca");
                }else {
                    mode.setSummary(" Mode Saat ini : Scrool");
                    mode.setTitle(" Mode Baca");
                }

            }
            return true;
        });


        modeCh.setOnPreferenceChangeListener((preference, newValue) -> {

            boolean newModeCh = (boolean) newValue;

            if (modeBahasa){

                if (newModeCh){
                    modeCh.setTitle(" List Chapter mode");
                    modeCh.setSummary(" Current mode : Grid x4");

                }else {
                    modeCh.setTitle(" List Chapter mode");
                    modeCh.setSummary(" Current mode : List");

                }

            }else {
                if (!newModeCh){
                    modeCh.setTitle(" Mode list bab");
                    modeCh.setSummary(" Mode saat ini : grid x4");
                }else {
                    modeCh.setTitle(" Mode list bab");
                    modeCh.setSummary( "Mode saat ini : List");
                }
            }
            return true;
        });

        Preference version = findPreference("version");
        assert version != null;
        version.setSummary("Package : " + BuildConfig.APPLICATION_ID + "\n" + "Version : " + BuildConfig.VERSION_NAME);


    }

    private void checkCurrentLanguage(){

        if (modeBahasa){
            ingrisLangague();

        } else {
            bahasaIndonesia();
        }

    }

    private void bahasaIndonesia(){

        bahasa.setTitle("Bahasa aplikasi");
        bahasa.setSummary(" Bahasa saat ini Indonesia");

        bug.setTitle("Laporkan Bug");
        cache.setSummary("Selalu simpan gambar pada cache\nJika diaktifkan maka ini akan menyimpan gambar pada cache aplikasi\nSehingga ketika lain waktu ditampilkan lagi ini tidak akan menggunakan data / wifi");
        quality.setTitle("Qualitas gambar");
        quality.setSummary("Selalu tampilkan qualitas terbaik");
        advance.setSummary("Ukuan kostum");
        advance.setSummary("Mengatur ukuran slider dan gambar detail komik secara kostum");
        server.setTitle("List Server");
        server.setSummary("Mengubah, dan melihat server yang tersedia\nDigunakan jika server utama error");

        if (modeSummary) {
            mode.setSummary(" Mode Saat ini : Slider");
            mode.setTitle(" Mode Baca");
        } else {
            mode.setSummary(" Mode Saat ini : Scrool");
            mode.setTitle(" Mode Baca");
        }

        if (modeChOrientation){
            modeCh.setTitle(" Mode list bab");
            modeCh.setSummary(" Mode saat ini : grid x4");
        }else {
            modeCh.setTitle(" Mode list bab");
            modeCh.setSummary( "Mode saat ini : List");
        }

        if (modeAnmasiDetail){
            animasiDetail.setTitle(" Animasi Detail");
            animasiDetail.setSummary("Selalu animasikan gambar detail komik");

        }else {
            animasiDetail.setTitle(" Animasi Detail");
            animasiDetail.setSummary("Selalu animasikan gambar detail komik");
        }

        if (modeAnmasiTransisi) {
            animasiTransisi.setTitle(" Animasi Transisi");
            animasiTransisi.setSummary(" Selalu animasikan transisi antar halaman");

        }else {
            animasiTransisi.setTitle(" Animasi Transisi");
            animasiTransisi.setSummary(" Selalu animasikan transisi antar halaman");
        }

    }

    private void ingrisLangague(){

        bahasa.setTitle("App language");
        bahasa.setSummary(" Current language Inggris");

        bug.setTitle("Report bug");
        cache.setSummary("Always save images in cache\nIf enabled, this will save images in the application cache\nSo that the next time they are displayed again this will not use data/wifi");
        quality.setTitle("Image Quality");
        quality.setSummary("Always show the best quality");
        advance.setSummary("Custum size");
        advance.setSummary("Adjust the size of the slider and draw comic details based on the costume");
        server.setTitle("List Servers");
        server.setSummary("Mengubah, dan melihat server yang tersedia\nDigunakan jika server utama error");

        if (modeSummary) {
            mode.setTitle(" Read mode");
            mode.setSummary(" Current mode : Slider");

        } else {
            mode.setTitle(" Read mode");
            mode.setSummary(" Current mode : Scrool");
        }

        if (modeChOrientation){
            modeCh.setTitle(" List Chapter mode");
            modeCh.setSummary(" Current mode : Grid x4");

        }else {
            modeCh.setTitle(" List Chapter mode");
            modeCh.setSummary(" Current mode : List");

        }

        if (modeAnmasiDetail){
            animasiDetail.setTitle(" Detail animation");
            animasiDetail.setSummary("Always animate detailed comic drawings");

        }else {
            animasiDetail.setTitle(" Detail animation");
            animasiDetail.setSummary(" Always animate detailed comic drawings");
        }

        if (modeAnmasiTransisi) {
            animasiTransisi.setTitle(" Transition Animation");
            animasiTransisi.setSummary(" Always animate transitions between pages");

        }else {
            animasiTransisi.setTitle(" Transition Animation");
            animasiTransisi.setSummary(" Always animate transitions between pages");
        }

    }
}