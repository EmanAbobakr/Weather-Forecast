package com.example.weather.Main.view

import android.app.Dialog
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import com.example.weather.R
import com.example.weather.favourite.view.FavouriteFragment
import com.example.weather.home.view.HomeFragment
import com.example.weather.settings.view.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val FirstTimePrefs = ""

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

        //first time checker - to show the dialog
        var fisrtTimeSharedPreferences: SharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        var firstTime = fisrtTimeSharedPreferences.getBoolean("firstTime", true)
        if(firstTime){
            dialog = Dialog(this)
            openDialopg()
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


//        homeFragment = HomeFragment()
//        fragmentManager = supportFragmentManager
//        var transaction : FragmentTransaction = fragmentManager.beginTransaction()
//        transaction.add(R.id.fragmentContainerView, homeFragment, "homeFragmentTag")
//        //transaction.addToBackStack("homeFragmentTag")
//        transaction.commit()

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

        var okBtn : Button = dialog.findViewById(R.id.okBtnId)

        okBtn.setOnClickListener{
            dialog.dismiss()
        }
        dialog.show()

        //change shared prefs
        var fisrtTimeSharedPreferences: SharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        var editor: SharedPreferences.Editor = fisrtTimeSharedPreferences.edit()
        editor.putBoolean("firstTime", false)
        editor.apply()
    }
}