package com.example.paraglidingquiz.Preferences;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.paraglidingquiz.R;

public class PreferencesFragment extends PreferenceFragmentCompat {

    private Preference resetSettings;

    private PreferenceManager preferenceManager;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference_main);

        preferenceManager = getPreferenceManager();
        sharedPreferences = preferenceManager.getSharedPreferences();

        resetSettings = findPreference(getString(R.string.reset));
        resetSettings.setOnPreferenceClickListener((Preference preference) -> {
            sharedPreferences.edit().clear().commit();

            this.getActivity().finish();
            this.getActivity().startActivity(this.getActivity().getIntent());
            return true;
        });
    }
}