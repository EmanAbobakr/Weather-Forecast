package com.example.weather.splashscreen.view

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch
import androidx.lifecycle.ViewModelProvider
import com.example.weather.Main.view.MainActivity
import com.example.weather.Main.viewmodel.MainVM
import com.example.weather.Main.viewmodel.MainVMFactory
import com.example.weather.R
import com.example.weather.splashscreen.viewmodel.SplashScreenVM
import com.example.weather.splashscreen.viewmodel.SplashScreenVMFactory
import kotlinx.coroutines.delay

class SplashScreen : AppCompatActivity() {

    lateinit var viewModel: SplashScreenVM
    lateinit var viewModelFactory: SplashScreenVMFactory

    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //View Model & Factory
        viewModelFactory = SplashScreenVMFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SplashScreenVM::class.java)

        //first time checker - to show the dialog
        var fisrtTimeSharedPreferences: SharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        var firstTime = fisrtTimeSharedPreferences.getBoolean("firstTime", true)
        if(firstTime){
            dialog = Dialog(this)
            openDialopg()
        }else{

            //LocaleManager.setLocale(this)
            val intent : Intent = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(intent)
        }
//
//        Handler().postDelayed({
//            val intent : Intent = Intent(this@SplashScreen, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }, 1000)
    }

    fun openDialopg(){
        dialog.setContentView(R.layout.initial_settings_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        var radioGroup : RadioGroup = dialog.findViewById(R.id.locationRadioGroupId)
        var radioButton : RadioButton
        var switch : Switch = dialog.findViewById(R.id.notificationSwitchId)
        var okBtn : Button = dialog.findViewById(R.id.okBtnId)

        okBtn.setOnClickListener{
            radioButton = dialog.findViewById(radioGroup.checkedRadioButtonId)
            viewModel.setupSettings(radioButton.text.toString(),switch.isChecked)
            dialog.dismiss()
            val intent : Intent = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(intent)
        }
        dialog.show()

        //change shared preference prefs
        var fisrtTimeSharedPreferences: SharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        var editor1: SharedPreferences.Editor = fisrtTimeSharedPreferences.edit()
        editor1.putBoolean("firstTime", false)
        editor1.apply()
    }
}