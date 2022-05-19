package com.example.weather.home.model.weatherrequest

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*

//OnRequestPermissionsResultCallback
class GPSLocation(var activity: Activity, var context: Context) : ILocation{

//    var  mFucedLocationClient: FusedLocationProviderClient
//    var PERMISSION_ID = 44

    lateinit var geocoder: Geocoder
    lateinit var addresses: List<Address>

    var coordinatesValues: MutableLiveData<List<Double>> = MutableLiveData<List<Double>>()



    init {
//        mFucedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    override fun getCoordinates() {

        Log.i("TAG", "getCoordinates: ")
        //requestNewLocationData()

//        if(checkPermissions()){
//            if(isLocationEnabled()){
//                requestNewLocationData()
//            }
//        }else{
//            requestPermissions()
//        }
    }

//    @SuppressLint("MissingPermission")
//    fun requestNewLocationData(){
//
//        Log.i("TAG", "requestNewLocationData: ")
//        var mLocationRequest : LocationRequest = LocationRequest.create()
//
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setInterval(5);
//        mLocationRequest.setFastestInterval(0);
//        mLocationRequest.setNumUpdates(1);
//
//        mFucedLocationClient = LocationServices.getFusedLocationProviderClient(context);
//        mFucedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper()!!);
//    }
//
//    private val mLocationCallback: LocationCallback = object : LocationCallback() {
//        override fun onLocationResult(locationResult: LocationResult) {
//            Log.i("TAG", "onLocationResult: ")
//            super.onLocationResult(locationResult)
//            val mLastLocation = locationResult.lastLocation
//
//            Log.i("TAG", "onLocationResult: ${mLastLocation.latitude} and ${mLastLocation.longitude}")
//            //coordinatesValues = listOf(mLastLocation.latitude, mLastLocation.longitude)
//            coordinatesValues.postValue(listOf(mLastLocation.latitude, mLastLocation.longitude))
//        }
//    }
//
//
//    private fun checkPermissions(): Boolean {
//        Log.i("TAG", "checkPermissions: ")
//        return ActivityCompat.checkSelfPermission(
//            context,
//            Manifest.permission.ACCESS_COARSE_LOCATION
//        ) ==
//                PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(
//                    context,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) ==
//                PackageManager.PERMISSION_GRANTED
//    }
//
//    private fun requestPermissions() {
//        Log.i("TAG", "requestPermissions: ")
//        ActivityCompat.requestPermissions(
//            activity, arrayOf(
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ),
//            PERMISSION_ID
//        )
//
//    }
//
//    fun isLocationEnabled(): Boolean{
//        var locationManager:LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        Log.i("TAG", "onRequestPermissionsResult: ")
//        if (requestCode == PERMISSION_ID) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                requestNewLocationData()
//            }
//        }
//
//    }


}