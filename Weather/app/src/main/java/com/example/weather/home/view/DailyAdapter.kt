package com.example.weather.home.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.weather.R
import com.example.weather.home.model.Daily
import com.example.weather.home.model.Hourly
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DailyAdapter (var context: Context, var dailyData: List<Daily>) : RecyclerView.Adapter<DailyAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var dayDay: TextView
        lateinit var dayIcon: ImageView
        lateinit var dayDesc: TextView
        lateinit var dayTemp: TextView
        lateinit var layout: View

        init {
            layout = itemView
            dayDay = layout.findViewById(R.id.dailyDayNameId)
            dayIcon = layout.findViewById(R.id.dailyIconId)
            dayDesc = layout.findViewById(R.id.dailyDescId)
            dayTemp = layout.findViewById(R.id.dailyTempId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyAdapter.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val v: View = inflater.inflate(R.layout.fragment_home_daily_row, parent, false)
        val vh: ViewHolder = ViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.dayDay.text = dailyData.get(position).dt.toString()
        holder.dayDay.text = timeStampToDate(dailyData.get(position).dt)
        var iconURl = "https://openweathermap.org/img/wn/"+dailyData.get(position).weather.get(0).icon+"@2x.png"
        Glide.with(context).load(iconURl)
            .apply(RequestOptions().override(200,200))
            .into(holder.dayIcon)
        holder.dayDesc.text = dailyData.get(position).weather.get(0).description
//        holder.dayTemp.text = dailyData.get(position).temp.toString()
        holder.dayTemp.text = dailyData.get(position).temp.min.toString() + "/" + dailyData.get(position).temp.max.toString()
    }

    override fun getItemCount() = dailyData.size

    fun timeStampToDate (dt : Long) : String{
        var date : Date = Date(dt * 1000)
        var dateFormat : DateFormat = SimpleDateFormat("EEE")
        return dateFormat.format(date)
    }

}