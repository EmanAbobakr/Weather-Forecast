package com.example.weather.settings.view

import android.os.Bundle
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.weather.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        var locationPreference: Preference? = findPreference("loc")
        //Log.i("TAG", "onCreatePreferences: " + locationPreference.va)
    }
}