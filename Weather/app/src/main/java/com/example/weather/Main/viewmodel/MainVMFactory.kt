package com.example.weather.Main.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MainVMFactory(private var context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(MainVM::class.java))
            MainVM(context) as T
        else
            throw IllegalArgumentException("View Model Class not found")
    }
}
