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
import com.example.weather.home.model.Hourly

class HourlyAdapter (var context: Context, var hourlyData: List<Hourly>) : RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //I thin I will use view binfing
        lateinit var hourText: TextView
        lateinit var hourIcon: ImageView
        lateinit var hourTemp: TextView
        lateinit var layout: View

        init {
            layout = itemView
            hourText = layout.findViewById(R.id.hourlyHourTextId)
            hourIcon = layout.findViewById(R.id.hourlyIconId)
            hourTemp = layout.findViewById(R.id.hourlyTempTextId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyAdapter.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val v: View = inflater.inflate(R.layout.fragment_home_hourly_row, parent, false)
        val vh: ViewHolder = ViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: HourlyAdapter.ViewHolder, position: Int) {
        holder.hourText.text = hourlyData.get(position).dt.toString()
        //holder.hourIcon
        //var iconURl = "http://openweathermap.org/img/wn/"+hourlyData.get(position).weather.get(0).icon+"@2x.png"
        //var iconURl = "http://openweathermap.org/img/wn/10d@2x.png"

//        var iconURl = "www.thesportsdb.com/images/icons/sports/soccer.png"
//        Glide.with(context).load(iconURl)
//            .apply(RequestOptions().override(200,200))
//            .into(holder.hourIcon)
        holder.hourTemp.text = hourlyData.get(position).temp.toString()
    }

    //override fun getItemCount() = hourlyData.size
    override fun getItemCount(): Int {
        if(hourlyData.isNotEmpty())
            return 24
        else
            return 0
    }


}