package com.example.weather.home.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.weather.R
import com.example.weather.db.room.WeatherLocalDataSource
import com.example.weather.home.model.*
import com.example.weather.home.model.weatherrequest.GPSLocation
import com.example.weather.home.viewmodel.HomeVM
import com.example.weather.home.viewmodel.HomeVMFactory
import com.example.weather.network.WeatherRemoteDataSource
import com.google.android.gms.location.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    lateinit var viewModel: HomeVM
    lateinit var viewModelFactory: HomeVMFactory

    lateinit var cityName: TextView
    lateinit var todayDate: TextView
    lateinit var todayWeatherDesc: TextView
    lateinit var todayTemp: TextView
    lateinit var currentIcon: ImageView

    lateinit var humidity: TextView
    lateinit var windSpeed: TextView
    lateinit var pressure: TextView
    lateinit var clouds: TextView


    lateinit var hourlyRecyclerView: RecyclerView
    lateinit var dailyRecyclerView: RecyclerView
    lateinit var layoutManagerH: RecyclerView.LayoutManager
    lateinit var layoutManagerV: RecyclerView.LayoutManager

    var weatherData: WeatherPojo? = null
    lateinit var hourlyData: List<Hourly>
    lateinit var dailyData: List<Daily>

    lateinit var hourlyAdapter: HourlyAdapter
    lateinit var dailyAdapter: DailyAdapter

    //GPS
    lateinit var  mFucedLocationClient: FusedLocationProviderClient
    var PERMISSION_ID = 44
    var coordinatesValues: MutableLiveData<List<Double>> = MutableLiveData<List<Double>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mFucedLocationClient = LocationServices.getFusedLocationProviderClient(view.context);

        initUI(view)

        //View Model & Factory
        viewModelFactory = HomeVMFactory(WeatherRepo(view.context, WeatherLocalDataSource(), WeatherRemoteDataSource()), view.context)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeVM::class.java)

        setUpRecycleViews()

        //viewModel.getWeather()

        viewModel.weatherData?.observe(viewLifecycleOwner, {
            weatherData = it

            cityName.text = weatherData?.timezone
//            todayDate.text = weatherData?.current?.dt.toString()
            todayDate.text = timeStampToDate(weatherData?.current?.dt!!)
            todayWeatherDesc.text = weatherData?.current?.weather?.get(0)?.description
            todayTemp.text = weatherData?.current?.temp.toString()

            var iconURl = "https://openweathermap.org/img/wn/"+weatherData?.current?.weather?.get(0)?.icon+"@2x.png"
            Glide.with(view.context).load(iconURl)
                .apply(RequestOptions().override(200,200))
                .into(currentIcon)

            hourlyAdapter.hourlyData = weatherData?.hourly?: emptyList()
            hourlyAdapter.notifyDataSetChanged()

            dailyAdapter.dailyData = weatherData?.daily?: emptyList()
            dailyAdapter.notifyDataSetChanged()

            humidity.text = weatherData?.current?.humidity.toString()
            windSpeed.text = weatherData?.current?.wind_speed.toString()
            pressure.text = weatherData?.current?.pressure.toString()
            clouds.text = weatherData?.current?.clouds.toString()

            //viewModel.getDateTime(hourlyData.get(0).dt)
        })


    }

    override fun onResume() {
        super.onResume()

        if (viewModel.isGPS()){
            if(checkPermissions()){
                if(isLocationEnabled()){
                    requestNewLocationData()
                }
            }else{
                requestPermissions()
            }
        }else{
            //call map code
        }

    }

    private fun initUI(view: View){

        cityName = view.findViewById(R.id.cityNameId)
        todayDate = view.findViewById(R.id.todayDateId)
        todayWeatherDesc = view.findViewById(R.id.todayWeatherId)
        todayTemp = view.findViewById(R.id.todayTempId)
        currentIcon = view.findViewById(R.id.currentIconId)

        humidity = view.findViewById(R.id.humidityInfId)
        windSpeed = view.findViewById(R.id.windspeedInfId)
        pressure = view.findViewById(R.id.pressureInfId)
        clouds = view.findViewById(R.id.cloudsInfId)

        hourlyRecyclerView = view.findViewById(R.id.hourlyRecycleViewId) as RecyclerView
        dailyRecyclerView = view.findViewById(R.id.dailyRecycleViewId) as RecyclerView

        hourlyAdapter = HourlyAdapter(view.context, weatherData?.hourly?: emptyList())
        hourlyRecyclerView.adapter = hourlyAdapter

        dailyAdapter = DailyAdapter(view.context, weatherData?.daily?: emptyList())
        dailyRecyclerView.adapter = dailyAdapter
    }

    private fun setUpRecycleViews(){
        layoutManagerH = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        hourlyRecyclerView.layoutManager = layoutManagerH

        layoutManagerV = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        dailyRecyclerView.layoutManager = layoutManagerV

    }


    //GPS
    @SuppressLint("MissingPermission")
    fun requestNewLocationData(){

        Log.i("TAG", "requestNewLocationData: ")
        var mLocationRequest : LocationRequest = LocationRequest.create()

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFucedLocationClient = LocationServices.getFusedLocationProviderClient(requireView().context);
        mFucedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper()!!);
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            Log.i("TAG", "onLocationResult: ")
            super.onLocationResult(locationResult)
            val mLastLocation = locationResult.lastLocation

            Log.i("TAG", "onLocationResult: ${mLastLocation.latitude} and ${mLastLocation.longitude}")
            viewModel.getWeather(mLastLocation.latitude, mLastLocation.longitude)
        }
    }


    private fun checkPermissions(): Boolean {
        Log.i("TAG", "checkPermissions: ")
        return ActivityCompat.checkSelfPermission(
            requireView().context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) ==
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireView().context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) ==
                PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        Log.i("TAG", "requestPermissions: ")
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )

    }

    fun isLocationEnabled(): Boolean{
        var locationManager: LocationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun timeStampToDate (dt : Long) : String{
        var date : Date = Date(dt * 1000)
        var dateFormat : DateFormat = SimpleDateFormat("MMM d")
        return dateFormat.format(date)
    }


}