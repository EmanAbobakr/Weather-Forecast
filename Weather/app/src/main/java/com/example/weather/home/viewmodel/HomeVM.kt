package com.example.weather.home.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.db.sharedpreferences.SharedPeferenceManager
import com.example.weather.home.model.IWeatherRepo
import com.example.weather.home.model.WeatherPojo

import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

class HomeVM(private var weatherRepo: IWeatherRepo, private var context: Context): ViewModel() {
    var mutableWeatherData : MutableLiveData<WeatherPojo> = MutableLiveData()
    var weatherData : LiveData<WeatherPojo>? = mutableWeatherData

    init {
        getWeather()
    }

    fun getWeather(){
        viewModelScope.launch(Dispatchers.IO) {
            mutableWeatherData.postValue(weatherRepo.getWeather(30.1342415430828, 31.24206894756661))
        }
    }



//    public fun getDateTime(s: Long): String? {
//        try {
//            val sdf = SimpleDateFormat("MM/dd/yyyy")
//            val netDate = Date(s * 1000)
//            return sdf.format(netDate)
//        } catch (e: Exception) {
//            return e.toString()
//        }
//
////        val stamp = Timestamp(System.currentTimeMillis())
////        val date = Date(stamp.time)
////        println(date)
//    }
}