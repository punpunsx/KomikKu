package com.komikatow.komiku.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.komikatow.komiku.BuildConfig;
import com.komikatow.komiku.R;

public final class FragmentSetting extends PreferenceFragmentCompat {
    private Preference bahasa;
    private Preference teknologi;
    private Preference developer;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.fragment_setting, rootKey);

        bahasa = findPreference("bahasa");
        teknologi = findPreference("teknologi");
        developer = findPreference("about");

        final SwitchPreference mode = findPreference("mode");
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        final boolean modeSummary = sharedPreferences.getBoolean("mode", false);

        if (modeSummary) {
            assert mode != null;
            mode.setSummary(" Mode Saat ini : Slide");
        } else {
            assert mode != null;
            mode.setSummary(" Mode Saat ini : Scrool");
        }

        Preference version = findPreference("version");
        assert version != null;
        version.setSummary("Package : " + BuildConfig.APPLICATION_ID + "\n" + "Version : " + BuildConfig.VERSION_NAME);


    }
}
