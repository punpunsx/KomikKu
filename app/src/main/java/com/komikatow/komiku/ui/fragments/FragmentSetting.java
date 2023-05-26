package com.komikatow.komiku.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.komikatow.komiku.BuildConfig;
import com.komikatow.komiku.R;

public final class FragmentSetting extends PreferenceFragmentCompat {
    private SwitchPreference mode;
    private Preference bahasa, version, teknologi, developer;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.fragment_setting, rootKey);

        mode = findPreference("mode");
        bahasa = findPreference("bahasa");
        version = findPreference("version");
        teknologi = findPreference("teknologi");
        developer = findPreference("about");


        version.setSummary("Package : "+BuildConfig.APPLICATION_ID+ "\n" +"Version : "+ BuildConfig.VERSION_NAME);
        mode.setOnPreferenceChangeListener((preference, newValue) -> {
            boolean status = (boolean) newValue;

            if (status){
                mode.setSummary(" Mode Saat ini : Slide");
            }else {
                mode.setSummary(" Mode Saat ini : Scrool");
            }

            return true;
        });

    }

}
