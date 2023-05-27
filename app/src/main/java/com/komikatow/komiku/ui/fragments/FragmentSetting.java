package com.komikatow.komiku.ui.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.komikatow.komiku.BuildConfig;
import com.komikatow.komiku.R;

public final class FragmentSetting extends PreferenceFragmentCompat {
    private SwitchPreference mode, animasiDetail, animasiTransisi, cache, quality;
    private Preference bahasa, bug;
    private boolean modeSummary;
    private boolean modeBahasa;
    private boolean modeAnmasiDetail;
    private boolean modeAnmasiTransisi;


    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.fragment_setting, rootKey);


        mode = findPreference("mode");
        animasiDetail = findPreference("animasiGambar");
        animasiTransisi = findPreference("animasiTransisi");
        cache = findPreference("cache");
        quality = findPreference("quality");

        bahasa = findPreference("bahasa");
        bug = findPreference("bug");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        modeSummary = sharedPreferences.getBoolean("mode", false);
        modeBahasa = sharedPreferences.getBoolean("bahasa", true);
        modeAnmasiDetail  = sharedPreferences.getBoolean("animasiGambar", false);
        modeAnmasiTransisi = sharedPreferences.getBoolean("animasiTransisi", false);

        bug.setOnPreferenceClickListener(preference -> {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://api.whatsapp.com/send/?phone=62895323021645&text&type=phone_number&app_absent=0"));
            startActivity(intent);

            return true;
        });

        checkBahasaSaatIni();
        bahasa.setOnPreferenceChangeListener((preference, newValue) -> {
            boolean newMode = (boolean)  newValue;

            if (newMode){

                bahasa.setTitle("App language");
                bahasa.setSummary(" Current language Inggris");

                bug.setTitle("Report bug");
                cache.setSummary("Always save images in cache\nIf enabled, this will save images in the application cache\nSo that the next time they are displayed again this will not use data/wifi");
                quality.setTitle("Image Quality");
                quality.setSummary("Always show the best quality");

                if (modeSummary) {
                    mode.setTitle(" Read mode");
                    mode.setSummary(" Current mode : Slide");
                } else {
                    mode.setTitle(" Read mode");
                    mode.setSummary(" Current mode : Scrool");
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

            } else {

                bahasa.setTitle("Bahasa aplikasi");
                bahasa.setSummary(" Bahasa saat ini Indonesia");

                bug.setTitle("Laporkan Bug");
                cache.setSummary("Selalu simpan gambar pada cache\nJika diaktifkan maka ini akan menyimpan gambar pada cache aplikasi\nSehingga ketika lain waktu ditampilkan lagi ini tidak akan menggunakan data / wifi");
                quality.setTitle("Qualitas gambar");
                quality.setSummary("Selalu tampilkan qualitas terbaik");

                if (modeSummary) {
                    mode.setSummary(" Mode Saat ini : Slide");
                    mode.setTitle(" Mode Baca");
                } else {
                    mode.setSummary(" Mode Saat ini : Scrool");
                    mode.setTitle(" Mode Baca");
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
            return true;
        });
        mode.setOnPreferenceChangeListener((preference, newValue) -> {
            boolean newModeBaca = (boolean) newValue;

            if (modeBahasa){
                if (newModeBaca){
                    mode.setTitle(" Read mode");
                    mode.setSummary(" Current mode : Slide");

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

        Preference version = findPreference("version");
        version.setSummary("Package : " + BuildConfig.APPLICATION_ID + "\n" + "Version : " + BuildConfig.VERSION_NAME);



    }

    private void checkBahasaSaatIni(){

        if (modeBahasa){

            bahasa.setTitle("App language");
            bahasa.setSummary(" Current language Inggris");

            bug.setTitle("Report bug");
            cache.setSummary("Always save images in cache\nIf enabled, this will save images in the application cache\nSo that the next time they are displayed again this will not use data/wifi");
            quality.setTitle("Image Quality");
            quality.setSummary("Always show the best quality");

            if (modeSummary) {
                mode.setSummary(" Current mode : Slide");
                mode.setTitle(" Read mode");
            } else {
                mode.setSummary(" Current mode : Scrool");
                mode.setTitle(" Read mode");
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


        } else {

            bahasa.setTitle("Bahasa aplikasi");
            bahasa.setSummary(" Bahasa saat ini Indonesia");

            bug.setTitle("Laporkan Bug");
            cache.setSummary("Selalu simpan gambar pada cache\nJika diaktifkan maka ini akan menyimpan gambar pada cache aplikasi\nSehingga ketika lain waktu ditampilkan lagi ini tidak akan menggunakan data / wifi");
            quality.setTitle("Qualitas gambar");
            quality.setSummary("Selalu tampilkan qualitas terbaik");

            if (modeSummary) {
                mode.setSummary(" Mode Saat ini : Slide");
                mode.setTitle(" Mode Baca");
            } else {
                mode.setSummary(" Mode Saat ini : Scrool");
                mode.setTitle(" Mode Baca");
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
    }

}
