package com.example.weather.network

import android.util.Log
import com.example.weather.home.model.Weather
import com.example.weather.home.model.WeatherPojo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRemoteDataSource : IWeatherRemoteDataSource {
    override suspend fun getWeatherOverNetwork(lat: Double, lon: Double, units: String, lang: String): WeatherPojo? {
        var weatherAPI = RetrofitHelper.getInstance().create(IRetrofitAPI::class.java)
        var response = weatherAPI.getWeather(lat, lon, units, lang)
        if(response.isSuccessful){
            return response.body()
        }else{
            return null
        }
    }
}