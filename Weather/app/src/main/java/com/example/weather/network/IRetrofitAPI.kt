package com.example.weather.network

import com.example.weather.home.model.WeatherPojo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofitAPI {
    @GET("data/2.5/onecall?exclude=alerts,minutely&appid=8d2874517e02ee82501220fa88467480")
    suspend fun getWeather(@Query("lat") lat: Double,
                           @Query("lon") lon: Double,
                           @Query("units") units: String = "metric",
                           @Query("lang") lang: String = "en"): Response<WeatherPojo>
}