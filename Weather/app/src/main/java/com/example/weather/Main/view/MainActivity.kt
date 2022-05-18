package com.example.weather.Main.view

import android.app.Dialog
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.weather.Main.viewmodel.MainVM
import com.example.weather.Main.viewmodel.MainVMFactory
import com.example.weather.R
import com.example.weather.db.LocaleManager
import com.example.weather.db.room.WeatherLocalDataSource
import com.example.weather.favourite.view.FavouriteFragment
import com.example.weather.home.model.WeatherRepo
import com.example.weather.home.view.HomeFragment
import com.example.weather.home.viewmodel.HomeVM
import com.example.weather.home.viewmodel.HomeVMFactory
import com.example.weather.network.WeatherRemoteDataSource
import com.example.weather.settings.view.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var viewModel: MainVM
    lateinit var viewModelFactory: MainVMFactory

    lateinit var dialog: Dialog

    lateinit var fragmentManager: FragmentManager
    lateinit var homeFragment: HomeFragment
    lateinit var favouriteFragment: FavouriteFragment
    var settingsFragment: SettingsFragment? = null

    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //View Model & Factory
        viewModelFactory = MainVMFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainVM::class.java)

        //first time checker - to show the dialog
        var fisrtTimeSharedPreferences: SharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        var firstTime = fisrtTimeSharedPreferences.getBoolean("firstTime", true)
        if(firstTime){
            dialog = Dialog(this)
            openDialopg()
        }else{
            LocaleManager.setLocale(applicationContext)
        }

        drawerLayout = findViewById(R.id.drawerLayoutId)
        navigationView = findViewById(R.id.navViewId)
        toolbar = findViewById(R.id.mainToolBarId)

        setSupportActionBar(toolbar)
        navigationView.bringToFront()

        var toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_navigation_drawer, R.string.close_navigation_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        navigationView.setCheckedItem(R.id.navHomeId)

        fragmentManager = supportFragmentManager

    }

    override fun onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.navHomeId -> {

            }

            R.id.navFavId -> {

                favouriteFragment = FavouriteFragment()
                var transaction : FragmentTransaction = fragmentManager.beginTransaction()
                transaction.add(R.id.fragmentContainerView, favouriteFragment, "favFragmentTag")
                //transaction.addToBackStack("favFragmentTag")
                transaction.commit()

            }

            R.id.navAlertId -> {

            }

            R.id.nav_setting -> {
                inflateSettingsFragement()
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }

    fun inflateSettingsFragement(){
        Log.i("TAG", "inflateSettingsFragement: ")
        if(settingsFragment == null){
            settingsFragment = SettingsFragment()
        }
        var transaction : FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, settingsFragment!!, "settingFragmentTag")
        //transaction.addToBackStack("settingFragmentTag")
        transaction.commit()
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
        }
        dialog.show()

        //change shared preference prefs
        var fisrtTimeSharedPreferences: SharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        var editor1: SharedPreferences.Editor = fisrtTimeSharedPreferences.edit()
        editor1.putBoolean("firstTime", false)
        editor1.apply()

    }
}