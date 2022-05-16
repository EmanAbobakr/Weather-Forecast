package com.example.weather.home.model

interface IWeatherRepo {
    suspend fun getWeather(lat: Double, lon: Double, units: String = "metric", lang: String = "en") : WeatherPojo?
}