package com.paulgiron.supercoolapp.Model

class Forecast(
    var id: Int,
    var weather_state_name: String,
    var weather_state_abbr: String,
    var wind_direction_compass: String,
    var created: String,
    var applicable_date: String,
    var min_temp: Int,
    var max_temp: Int,
    var the_temp: Int,
    var wind_speed: Int,
    var wind_direction: Int,
    var air_pressure: Int,
    var humidity: Int,
//    var visibility: Int = 0,
    var predictability: Int
) { }