package com.example.weather.db.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//
//object SharedPeferenceManager {
//    fun getInstance(): SharedPeferenceManager {
//        return SharedPeferenceManager
//    }
//
//    fun setValue(){
//        var settingsSharedPreferences: SharedPreferences = getSharedPreferences("com.example.weather_preferences", AppCompatActivity.MODE_PRIVATE)
//        var editor2: SharedPreferences.Editor = settingsSharedPreferences.edit()
//    }
//
//}

class SharedPeferenceManager private constructor(var context: Context) {

    //private var sharedPreferences: SharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    //private var editor: SharedPreferences.Editor = sharedPreferences.edit()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    companion object{
        private var sharedPrefManger: SharedPeferenceManager? = null

        fun getInstance(context: Context): SharedPeferenceManager {
            return sharedPrefManger ?: SharedPeferenceManager(context)
        }
    }

//    fun getAppSharedPref(): SharedPreferences {
//        return sharedPreferences
//    }

    fun setValue(key: String, value: Any, fileName: String) {
        sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        when (value) {
            is Int -> {
                editor.putInt(key, value)
            }
            is String -> {
                editor.putString(key, value)
            }
            is Float -> {
                editor.putFloat(key, value)
            }
            is Long -> {
                editor.putLong(key, value)
            }
            is Boolean -> {
                editor.putBoolean(key, value)
            }
        }
        editor.apply()
    }

    fun getStringValue(key: String, defaultValue: String, fileName: String): String {
        sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, defaultValue).toString()
    }

    fun getIntValue(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }


    fun getFloatValue(key: String, defaultValue: Float = 0f): Float {
        return sharedPreferences.getFloat(key, defaultValue)
    }

    fun getLongValue(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    fun getBooleanValue(keyFlag: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(keyFlag, defaultValue)
    }

    fun removeKey(key: String) {
        editor.remove(key)
        editor.apply()
    }

    fun clear() {
        editor.clear().apply()
    }
}

