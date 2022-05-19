package com.example.weather.home.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.weather.db.sharedpreferences.SharedPeferenceManager
import com.example.weather.home.model.IWeatherRepo
import com.example.weather.home.model.WeatherPojo
import com.example.weather.home.model.weatherrequest.GPSLocation

import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

class HomeVM(private var weatherRepo: IWeatherRepo, private var context: Context): ViewModel() {
    var mutableWeatherData : MutableLiveData<WeatherPojo> = MutableLiveData()
    var weatherData : LiveData<WeatherPojo>? = mutableWeatherData
    //var gps : GPSLocation
    var coordinatesValues: List<Double> = listOf()

    init {

    }

    fun isGPS() : Boolean{
        var location = SharedPeferenceManager.getInstance(context).getStringValue("key_loc", "gps", "com.example.weather_preferences")
        if (location == "gps")
            return true
        else
            return false
    }

    fun getWeather(lat: Double, lon: Double){
        viewModelScope.launch(Dispatchers.IO) {
            var units = SharedPeferenceManager.getInstance(context).getStringValue("key_temp", "celsius", "com.example.weather_preferences")
            var language = SharedPeferenceManager.getInstance(context).getStringValue("key_lang", "en", "com.example.weather_preferences")

            Log.i("TAG", "getWeather: I will fetch data for lat $lat and lon $lon and language $language and units $units")
            if (units == "kelvin")
                mutableWeatherData.postValue(weatherRepo.getWeather(lat, lon, lang = language))
            else{
                if(units == "celsius")
                    units = "metric"
                else if(units == "fahrenheit")
                    units = "imperial"

                mutableWeatherData.postValue(weatherRepo.getWeather(lat, lon, units ,language))
            }

        }
    }


    fun getRequestData(){

    }

//    fun getWeather(){
//        viewModelScope.launch(Dispatchers.IO) {
//            mutableWeatherData.postValue(weatherRepo.getWeather(30.1342415430828, 31.24206894756661))
//        }
//    }



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