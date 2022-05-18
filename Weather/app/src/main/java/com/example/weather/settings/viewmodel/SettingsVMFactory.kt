package com.example.weather.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class SettingsVMFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(SettingsVM::class.java))
            SettingsVM() as T
        else
            throw IllegalArgumentException("View Model Class not found")
    }
}