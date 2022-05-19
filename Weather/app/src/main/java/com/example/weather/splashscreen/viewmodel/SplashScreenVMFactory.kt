package com.example.weather.splashscreen.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.Main.viewmodel.MainVM
import java.lang.IllegalArgumentException

class SplashScreenVMFactory(private var context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(SplashScreenVM::class.java))
            SplashScreenVM(context) as T
        else
            throw IllegalArgumentException("View Model Class not found")
    }
}
