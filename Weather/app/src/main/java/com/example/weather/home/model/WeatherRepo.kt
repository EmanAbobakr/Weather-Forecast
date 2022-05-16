package com.example.weather.home.model

import android.content.Context
import android.util.Log
import com.example.weather.db.IWeatherLocalDataSource
import com.example.weather.network.IWeatherRemoteDataSource

class WeatherRepo (var context: Context, var localDataSource: IWeatherLocalDataSource, var remoteDataSource: IWeatherRemoteDataSource) : IWeatherRepo {
    override suspend fun getWeather(lat: Double, lon: Double, units: String, lang: String): WeatherPojo? {
        return remoteDataSource.getWeatherOverNetwork(lat, lon, units, lang)
    }
}