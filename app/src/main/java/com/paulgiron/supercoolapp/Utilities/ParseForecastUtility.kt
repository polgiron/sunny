package com.paulgiron.supercoolapp.Utilities

import android.util.Log
import com.paulgiron.supercoolapp.Model.Forecast
import khronos.Dates
import khronos.days
import khronos.plus
import khronos.toString
import org.json.JSONArray
import org.json.JSONObject

class ParseForecastUtility {
    fun parseForecast(jsonData: String?) : ArrayList<Forecast> {
        var jsonObject: JSONObject = JSONObject(jsonData)
        val jsonArray: JSONArray = jsonObject.getJSONArray("consolidated_weather")
//        val jsonArray = JSONArray(jsonData)
//        Log.i("TEST", jsonArray.toString())
        var forecastList: ArrayList<Forecast> = ArrayList()


        val thirdDay = Dates.today + 2.days
        val fourthDay = Dates.today + 3.days
        val fifthDay = Dates.today + 4.days
        val sixthDay = Dates.today + 5.days
        val days = listOf<String>(
            "Today",
            "Tomorrow",
            thirdDay.toString("EEEE"),
            fourthDay.toString("EEEE"),
            fifthDay.toString("EEEE"),
            sixthDay.toString("EEEE")
        )

        for (i in 0..(jsonArray.length() - 1)) {
            val jsonObject = jsonArray.getJSONObject(i)

            var forecast: Forecast = Forecast(
                jsonObject.getInt("id"),
                jsonObject.getString("weather_state_name"),
                jsonObject.getString("weather_state_abbr"),
                jsonObject.getString("wind_direction_compass"),
                jsonObject.getString("created"),
                jsonObject.getString("applicable_date"),
                jsonObject.getInt("min_temp"),
                jsonObject.getInt("max_temp"),
                jsonObject.getInt("the_temp"),
                jsonObject.getInt("wind_speed"),
                jsonObject.getInt("wind_direction"),
                jsonObject.getInt("air_pressure"),
                jsonObject.getInt("humidity"),
//                jsonObject.getInt("visibility"),
                jsonObject.getInt("predictability"),
                days[i]
            )



//            var forecast: Forecast = Forecast()
//
//            with(jsonObject) {
//                forecast.id = jsonObject.getInt("id")
//                forecast.weather_state_name = jsonObject.getString("weather_state_name")
//                forecast.weather_state_abbr = jsonObject.getString("weather_state_abbr")
//                forecast.wind_direction_compass = jsonObject.getString("wind_direction_compass")
//                forecast.created = jsonObject.getString("created")
//                forecast.applicable_date = jsonObject.getString("applicable_date")
//                forecast.min_temp = jsonObject.getInt("min_temp")
//                forecast.max_temp = jsonObject.getInt("max_temp")
//                forecast.the_temp = jsonObject.getInt("the_temp")
//                forecast.wind_speed = jsonObject.getInt("wind_speed")
//                forecast.wind_direction = jsonObject.getInt("wind_direction")
//                forecast.air_pressure = jsonObject.getInt("air_pressure")
//                forecast.humidity = jsonObject.getInt("humidity")
//                forecast.visibility = jsonObject.getInt("visibility")
//                forecast.predictability = jsonObject.getInt("predictability")
//            }

            forecastList.add(forecast)
        }

        return forecastList
    }

    fun parseCity(jsonData: String?) : String {
        var jsonObject = JSONObject(jsonData)
        return jsonObject["title"].toString()
    }

    fun parseCountry(jsonData: String?) : String {
        var jsonObject = JSONObject(jsonData)
        var parent = JSONObject(jsonObject["parent"].toString())
        return parent["title"].toString()
    }
}