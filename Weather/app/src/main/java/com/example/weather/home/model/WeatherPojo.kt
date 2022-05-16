package com.example.weather.home.model

data class WeatherPojo (var timezone: String,
                   var current: Current,
                   var hourly: List<Hourly>,
                   var daily: List<Daily>){

}

data class Current(var dt: Long,
              var weather: List<Weather>,
              var temp: Double,
              var pressure: Int,
              var humidity: Int,
              var wind_speed:Double,
              var clouds: Int,
              var uvi: Double,
              var visibility: Int){

}

data class Hourly(var dt: Long,
                  var temp: Double,
                  var weather: List<Weather>){

}

data class Daily(var dt: Long,
                 var weather: List<Weather>,
                 var temp: Temp){

}

data class Temp(var min: Double, var max: Double) {

}

data class Weather(var description: String, var icon: String) {

}

