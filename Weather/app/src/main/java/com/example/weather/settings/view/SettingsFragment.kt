package com.example.weather.settings.view

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.weather.R
import com.example.weather.db.LocaleManager
import com.example.weather.settings.viewmodel.SettingsVM
import com.example.weather.settings.viewmodel.SettingsVMFactory
import java.util.*

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener{

    lateinit var viewModel: SettingsVM
    lateinit var viewModelFactory: SettingsVMFactory

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //View Model & Factory
        viewModelFactory = SettingsVMFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(SettingsVM::class.java)

        setPreferenceChangeListeners()

    }

    fun setPreferenceChangeListeners(){
        // set Preference Change Listeners
        val languagePreference: Preference? = preferenceManager.findPreference("key_lang")
        languagePreference?.setOnPreferenceChangeListener(this)

        val locationPreference: Preference? = preferenceManager.findPreference("key_loc")
        locationPreference?.setOnPreferenceChangeListener(this)

        val temperaturePreference: Preference? = preferenceManager.findPreference("key_temp")
        temperaturePreference?.setOnPreferenceChangeListener(this)

        val windSpeedPreference: Preference? = preferenceManager.findPreference("key_wind_speed")
        windSpeedPreference?.setOnPreferenceChangeListener(this)

        val notificationPreference: Preference? = preferenceManager.findPreference("key_notification")
        notificationPreference?.setOnPreferenceChangeListener(this)
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
        //preference.title = "can it?"
        Log.i("TAG", "onPreferenceChange: preference " + preference)
        Log.i("TAG", "onPreferenceChange: value " + newValue.toString())
        when(newValue.toString()){
            //language
            "en" -> updateLanguagePreference("en")
            "ar" -> updateLanguagePreference("ar")
        }
        return true
    }

    fun updateLanguagePreference(lang: String){
        LocaleManager.setLocale(requireContext())
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

    }

}