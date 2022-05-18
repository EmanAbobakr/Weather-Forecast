package com.example.weather.Main.viewmodel.weatherrequest

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kotlinx.coroutines.withContext
import kotlin.math.log

class GPSLocation(var activity: Activity, var context: Context) : ILocation {

    lateinit var  mFucedLocationClient: FusedLocationProviderClient
    lateinit var geocoder: Geocoder
    lateinit var addresses: List<Address>
    //lateinit var coordinatesValues: List<Double>
    var coordinatesValues: List<Double> = listOf()

    var PERMISSION_ID = 44

    init {
        mFucedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    override fun getCoordinates(): List<Double> {

        Log.i("TAG", "getCoordinates: ")
        requestNewLocationData()

        if(checkPermissions()){
            requestNewLocationData();
        }else{
            requestPermissions();
        }
        return coordinatesValues
    }

    @SuppressLint("MissingPermission")
    fun requestNewLocationData(){
        var mLocationRequest : LocationRequest = LocationRequest.create()

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFucedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mFucedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper()!!);
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val mLastLocation = locationResult.lastLocation

            Log.i("TAG", "onLocationResult: ${mLastLocation.latitude} and ${mLastLocation.longitude}")
            coordinatesValues = listOf(mLastLocation.latitude, mLastLocation.longitude)
        }
    }


    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) ==
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) ==
                PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            activity, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

//    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>?, grantResults: IntArray) {
//        super.onRequestPermissionsResult(
//            requestCode, permissions,
//            grantResults
//        )
//        if (requestCode == PERMISSION_ID) {
//            if (grantResults[0] ==
//                PackageManager.PERMISSION_GRANTED
//            ) {
//                getLastLocation()
//            }
//        }
//    }



}