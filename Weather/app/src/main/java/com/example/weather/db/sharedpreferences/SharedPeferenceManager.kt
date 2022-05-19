package com.example.weather.db.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SharedPeferenceManager private constructor(var context: Context) {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    companion object{
        private var sharedPrefManger: SharedPeferenceManager? = null

        fun getInstance(context: Context): SharedPeferenceManager {
            return sharedPrefManger ?: SharedPeferenceManager(context)
        }
    }

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

}

