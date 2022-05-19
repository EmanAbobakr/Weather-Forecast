package com.example.weather.map

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
import com.example.weather.Main.view.MainActivity
import com.example.weather.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var okBtn : Button

    val PERMISSION_ID = 44
    val DEFAULT_ZOOM =15f
    lateinit var mMap : GoogleMap
    lateinit var mFucedLocationProviderClient: FusedLocationProviderClient

    var lat : Double = 0.0
    var lon : Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        okBtn = findViewById(R.id.mapBtnId)

        okBtn.setOnClickListener{

            var coordinatesPrefs: SharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
            var editor: SharedPreferences.Editor = coordinatesPrefs.edit()
            editor.putFloat("lat", lat.toFloat())
            editor.putFloat("lon", lon.toFloat())
            editor.apply()

            var intent : Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

        Log.i("TAG", "onCreate: Hello from the map")
        if(getLocationPermission()){
            initMap()

            //moveCamera(LatLng(, ), DEFAULT_ZOOM)
        }else{
            requestLocationPermission()
        }
    }

    private fun getLocationPermission() : Boolean{
        Log.i("TAG", "getLocationPermission: get location permission")
        return (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestLocationPermission(){
        Log.i("TAG", "requestLocationPermission: ")
        ActivityCompat.requestPermissions(this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.INTERNET
            ),
            PERMISSION_ID)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Log.i("TAG", "onRequestPermissionsResult: ")
        when(requestCode){
            PERMISSION_ID -> {
                if(grantResults.size > 0){
//                    for(i in 0..grantResults.size){
//                        if (grantResults.get(i) != PackageManager.PERMISSION_GRANTED){
//                            Log.i("TAG", "onRequestPermissionsResult: permission failed")
//                            return
//                        }
//                    }
//                    Log.i("TAG", "onRequestPermissionsResult: permission granted")
//                    //initialize the map
//                    initMap()
                    if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        initMap()
                    }
                }
            }
        }

    }

    private fun initMap(){

        var mapFragment : SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        //mapFragment.getMapAsync(callback)
        mapFragment.getMapAsync(this )
    }

    private val callback = OnMapReadyCallback { googleMap ->
        //mMap = googleMap

    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0

        var markerOptions : MarkerOptions = MarkerOptions().position(LatLng(30.03576108032025,  31.2150725023348)).title("Location")

        mMap.addMarker(markerOptions)
        mMap.setOnMapClickListener {
            var markerOptions : MarkerOptions = MarkerOptions().position(it).title("Location")
            mMap.clear()
            mMap.addMarker(markerOptions)
            mMap.animateCamera(CameraUpdateFactory.newLatLng(it))

            //it.latitude
            //it.longitude

            lat = it.latitude
            lon = it.longitude
            Log.i("TAG", "onMapReady: Hello from map lat = $lat and lon = $lon")
        }
    }

    private fun getDeviceLocation(){
        mFucedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        try {
            if(getLocationPermission()){
                var location = mFucedLocationProviderClient.lastLocation
                location.addOnCompleteListener(object : OnCompleteListener<Location> {
                    override fun onComplete(p0: Task<Location>) {
                        Log.i("TAG", "onComplete: found location")
                        var currentLocation : Location = p0.result as Location
                        Log.i("TAG", "onComplete: lat = ${currentLocation.latitude} lon  = ${currentLocation.longitude} ")
                        moveCamera(LatLng(currentLocation.latitude, currentLocation.longitude), DEFAULT_ZOOM)
                    }
                })
            }
        }catch (e: SecurityException){
            Log.i("TAG", "getDeviceLocation: ${e.message}")
        }
    }

    private fun moveCamera(latLng: LatLng, zoom: Float, title: String = "Location"){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))

        var options : MarkerOptions = MarkerOptions().position(latLng).title(title)
        mMap.addMarker(options)
    }
}