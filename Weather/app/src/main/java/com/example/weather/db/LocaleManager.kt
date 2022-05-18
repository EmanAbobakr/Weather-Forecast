package com.example.weather.db

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.weather.db.sharedpreferences.SharedPeferenceManager
import java.util.*

class LocaleManager {
    companion object{

        fun setLocale(context: Context){
            update(context, getLanguage(context))
        }

        fun update(context: Context, language: String){
            updateResources(context, language)
            var appContext : Context = context.applicationContext
            if(context != appContext){
                updateResources(appContext, language)
            }
        }

        fun getLanguage(context: Context) : String{
            var lang = SharedPeferenceManager.getInstance(context).getStringValue("key_lang", "english", "com.example.weather_preferences")
            Log.i("TAG", "getLanguage: lang" + lang)
            if(lang == "arabic")
                return "ar"
            return "en"
        }

        @SuppressLint("ObsoleteSdkInt")
        fun updateResources(context: Context, language: String){
            var locale : Locale = Locale(language)
            Locale.setDefault(locale)

            var resources : Resources = context.resources
            var config : Configuration = Configuration(resources.configuration)
            if(Build.VERSION.SDK_INT >= 17){
                config.setLocale(locale)
            }else{
                config.locale = locale
            }

            resources.updateConfiguration(config, resources.displayMetrics)
        }
    }
}