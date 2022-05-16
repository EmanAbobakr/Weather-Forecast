package com.example.weather.home.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.home.model.Daily
import com.example.weather.home.model.Hourly

class DailyAdapter (var context: Context, var dailyData: List<Daily>) : RecyclerView.Adapter<DailyAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        lateinit var hourText: TextView
//        lateinit var hourIcon: ImageView
//        lateinit var hourTemp: TextView
//        lateinit var layout: View
//
//        init {
//            layout = itemView
//            hourText = layout.findViewById(R.id.hourlyHourTextId)
//            hourIcon = layout.findViewById(R.id.hourlyIconId)
//            hourTemp = layout.findViewById(R.id.hourlyTempTextId)
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyAdapter.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val v: View = inflater.inflate(R.layout.fragment_home_daily_row, parent, false)
        val vh: ViewHolder = ViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount() = dailyData.size

}