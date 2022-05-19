package com.example.weather.Main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.weather.Main.viewmodel.MainVM
import com.example.weather.Main.viewmodel.MainVMFactory
import com.example.weather.home.model.weatherrequest.GPSLocation
import com.example.weather.R
import com.example.weather.alert.view.AlertFragment
import com.example.weather.favourite.view.FavouriteFragment
import com.example.weather.home.view.HomeFragment
import com.example.weather.settings.view.SettingsFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var viewModel: MainVM
    lateinit var viewModelFactory: MainVMFactory

    lateinit var fragmentManager: FragmentManager

    var homeFragment: HomeFragment? = null
    var favouriteFragment: FavouriteFragment? = null
    var alertFragment: AlertFragment? = null
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
                inflateHomeFragement()
            }

            R.id.navFavId -> {
                inflateFavouriteFragement()
            }

            R.id.navAlertId -> {
                inflateAlertFragement()
            }

            R.id.nav_setting -> {
                inflateSettingsFragement()
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }


    fun inflateHomeFragement(){
        Log.i("TAG", "inflateHomeFragement: ")
        if(homeFragment == null){
            homeFragment = HomeFragment()
        }
        var transaction : FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, homeFragment!!, "homeFragmentTag")
        //transaction.addToBackStack("homeFragmentTag")
        transaction.commit()
    }

    private fun inflateFavouriteFragement() {
        Log.i("TAG", "inflateFavFragement: ")
        if(favouriteFragment == null){
            favouriteFragment = FavouriteFragment()
        }
        var transaction : FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, favouriteFragment!!, "favFragmentTag")
        //transaction.addToBackStack("favFragmentTag")
        transaction.commit()
    }

    private fun inflateAlertFragement() {
        Log.i("TAG", "inflateAlertFragement: ")
        if(alertFragment == null){
            alertFragment = AlertFragment()
        }
        var transaction : FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, alertFragment!!, "alertFragmentTag")
        //transaction.addToBackStack("alertFragmentTag")
        transaction.commit()
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


}