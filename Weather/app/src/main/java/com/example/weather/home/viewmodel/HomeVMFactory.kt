package com.example.weather.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.home.model.IWeatherRepo
import com.example.weather.home.model.WeatherRepo
import java.lang.IllegalArgumentException

class HomeVMFactory(private var weatherRepo: IWeatherRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(HomeVM::class.java))
            HomeVM(weatherRepo) as T
        else
            throw IllegalArgumentException("View Model Class not found")
    }
}