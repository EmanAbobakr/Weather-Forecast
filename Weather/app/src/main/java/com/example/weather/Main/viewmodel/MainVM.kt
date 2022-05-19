package com.example.weather.Main.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.weather.db.sharedpreferences.SharedPeferenceManager
import java.util.*

class MainVM(private var context: Context) : ViewModel(){

//    fun setupSettings(location: String, notificationChecker: Boolean){
//
//        Log.i("TAG", "setupSettings: you initial setting language = ${Locale.getDefault().getLanguage()} location = $location units = celsius & meter_sec alerts = $notificationChecker")
//
//        if (Locale.getDefault().getLanguage() == "ar")
//            SharedPeferenceManager.getInstance(context).setValue("key_lang", "ar", "com.example.weather_preferences")
//        else
//            SharedPeferenceManager.getInstance(context).setValue("key_lang", "en", "com.example.weather_preferences")
//
//        if(location == "Map")
//            SharedPeferenceManager.getInstance(context).setValue("key_loc", "map", "com.example.weather_preferences")
//        else
//            SharedPeferenceManager.getInstance(context).setValue("key_loc", "gps", "com.example.weather_preferences")
//
//        SharedPeferenceManager.getInstance(context).setValue("key_temp", "celsius", "com.example.weather_preferences")
//        SharedPeferenceManager.getInstance(context).setValue("key_wind_speed", "meter_sec", "com.example.weather_preferences")
//
//        if(notificationChecker)
//            SharedPeferenceManager.getInstance(context).setValue("key_notification", true, "com.example.weather_preferences")
//        else
//            SharedPeferenceManager.getInstance(context).setValue("key_notification", false, "com.example.weather_preferences")
//
//    }
}