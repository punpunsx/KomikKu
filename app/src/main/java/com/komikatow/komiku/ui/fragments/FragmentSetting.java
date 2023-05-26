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

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.fragment_setting, rootKey);


        final SwitchPreference mode = findPreference("mode");
        final Preference bahasa = findPreference("bahasa");
        final Preference bug = findPreference("bug");

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


        assert bug != null;
        bug.setOnPreferenceClickListener(preference -> {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://api.whatsapp.com/send/?phone=62895323021645&text&type=phone_number&app_absent=0"));
            startActivity(intent);

            return true;
        });
    }

}
