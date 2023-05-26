package com.komikatow.komiku.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.komikatow.komiku.R;
import com.komikatow.komiku.room.dbApp.SettingDbApp;
import com.komikatow.komiku.room.enity.SettingEnity;

public final class FragmentSetting extends PreferenceFragmentCompat {
    private SettingDbApp settingDbApp;
    private SwitchPreference quality, mode, cache, animasiDetail, animasiTransisi;
    private Preference bahasa, version, teknologi, developer;
    boolean isQualityOn, isCacheOn, isModeScrool, isAnimasiDetailOn, isAnimasiTransisiOn;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.fragment_setting, rootKey);

        settingDbApp = SettingDbApp.getInstance(getContext());
        quality = findPreference("quality");
        mode = findPreference("mode");
        cache = findPreference("cache");
        animasiDetail = findPreference("animasiGambar");
        animasiTransisi = findPreference("animasiTransisi");

        bahasa = findPreference("bahasa");
        version = findPreference("version");
        teknologi = findPreference("teknologi");
        developer = findPreference("about");

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        isQualityOn = sharedPreferences.getBoolean("quality", false);
        isModeScrool  = sharedPreferences.getBoolean("mode", false);
        isCacheOn = sharedPreferences.getBoolean("cache", false);
        isAnimasiDetailOn = sharedPreferences.getBoolean("animasiGambar", false);
        isAnimasiTransisiOn = sharedPreferences.getBoolean("animasiTransisi", false);

        setOnAllSwitchChangeValue();
    }

    private void setOnAllSwitchChangeValue(){

        quality.setOnPreferenceChangeListener((preference, newValue) -> {
            isQualityOn = (boolean) newValue;
            new Thread(() -> {

                SettingEnity settingEnity = new SettingEnity();
                settingEnity.setQuality(isQualityOn);
                settingDbApp.dao().insert(settingEnity);

            }).start();

            return true;
        });

        mode.setOnPreferenceChangeListener((preference, newValue) -> {
            isModeScrool = (boolean) newValue;
            new Thread(() -> {

                SettingEnity settingEnity = new SettingEnity();
                settingEnity.setMode(isModeScrool);
                settingDbApp.dao().insert(settingEnity);

            }).start();

            if (isModeScrool){
                mode.setSummary("Mode saat ini : Scrool");
            }else {
                mode.setSummary("Mode saat ini : Slide");
            }

            return true;
        });

        cache.setOnPreferenceChangeListener((preference, newValue) -> {
            isCacheOn = (boolean) newValue;
            new Thread(() -> {

                SettingEnity settingEnity = new SettingEnity();
                settingEnity.setCache(isCacheOn);
                settingDbApp.dao().insert(settingEnity);

            }).start();
            return true;
        });

        animasiDetail.setOnPreferenceChangeListener((preference, newValue) -> {
            isAnimasiDetailOn = (boolean) newValue;
            new Thread(() -> {

                SettingEnity settingEnity = new SettingEnity();
                settingEnity.setAnimasiDetail(isAnimasiDetailOn);
                settingDbApp.dao().insert(settingEnity);

            }).start();

            return true;
        });

        animasiTransisi.setOnPreferenceChangeListener((preference, newValue) -> {
            isAnimasiTransisiOn = (boolean) newValue;
            new Thread(() -> {

                SettingEnity settingEnity = new SettingEnity();
                settingEnity.setTransisi(isAnimasiTransisiOn);
                settingDbApp.dao().insert(settingEnity);

            }).start();

            return true;
        });
    }
}
