package com.komikatow.komiku.ui.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.komikatow.komiku.BuildConfig;
import com.komikatow.komiku.R;

public final class FragmentSetting extends PreferenceFragmentCompat {
    private SwitchPreference quality, mode, cache, animasiDetail, animasiTransisi;
    private Preference bahasa, version, teknologi, developer;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.fragment_setting, rootKey);

        quality = findPreference("quality");
        mode = findPreference("mode");
        cache = findPreference("cache");
        animasiDetail = findPreference("animasiGambar");
        animasiTransisi = findPreference("animasiTransisi");

        bahasa = findPreference("bahasa");
        version = findPreference("version");
        teknologi = findPreference("teknologi");
        developer = findPreference("about");


        version.setSummary("Version : "+ BuildConfig.VERSION_NAME + "\nPackage : "+BuildConfig.APPLICATION_ID);

    }

}
