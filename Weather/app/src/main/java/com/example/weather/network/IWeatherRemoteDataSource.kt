package com.example.weather.network

import com.example.weather.home.model.Weather
import com.example.weather.home.model.WeatherPojo
import retrofit2.http.Query

interface IWeatherRemoteDataSource {
    suspend fun getWeatherOverNetwork(lat: Double, lon: Double, units: String = "metric", lang: String = "en") : WeatherPojo?
}