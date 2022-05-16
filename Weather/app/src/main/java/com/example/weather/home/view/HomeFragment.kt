package com.example.weather.home.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.db.IWeatherLocalDataSource
import com.example.weather.db.WeatherLocalDataSource
import com.example.weather.home.model.*
import com.example.weather.home.viewmodel.HomeVM
import com.example.weather.home.viewmodel.HomeVMFactory
import com.example.weather.network.IRetrofitAPI
import com.example.weather.network.RetrofitHelper
import com.example.weather.network.WeatherRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {

    lateinit var viewModel: HomeVM
    lateinit var viewModelFactory: HomeVMFactory

    lateinit var hourlyRecyclerView: RecyclerView
    lateinit var dailyRecyclerView: RecyclerView
    lateinit var layoutManagerH: RecyclerView.LayoutManager
    lateinit var layoutManagerV: RecyclerView.LayoutManager

    var weatherData: WeatherPojo? = null
    lateinit var hourlyData: List<Hourly>
    lateinit var dailyData: List<Daily>

    lateinit var hourlyAdapter: HourlyAdapter
    lateinit var dailyAdapter: DailyAdapter

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

        initUI(view)

        //View Model & Factory
        viewModelFactory = HomeVMFactory(WeatherRepo(view.context, WeatherLocalDataSource(), WeatherRemoteDataSource()))
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeVM::class.java)

        setUpRecycleViews()

        viewModel.weatherData?.observe(viewLifecycleOwner, {
            weatherData = it

            hourlyAdapter.hourlyData = weatherData?.hourly?: emptyList()
            hourlyAdapter.notifyDataSetChanged()

            dailyAdapter.dailyData = weatherData?.daily?: emptyList()
            dailyAdapter.notifyDataSetChanged()

            //viewModel.getDateTime(hourlyData.get(0).dt)
        })
    }

    private fun initUI(view: View){

        hourlyRecyclerView = view.findViewById(R.id.hourlyRecycleViewId) as RecyclerView
        dailyRecyclerView = view.findViewById(R.id.dailyRecycleViewId)

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


}