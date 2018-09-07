package com.paulgiron.supercoolapp.ViewHolders

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.RequestManager
import com.paulgiron.supercoolapp.Model.Forecast
import khronos.Dates.today
import khronos.days
import khronos.minus
import khronos.plus
import khronos.toString
import kotlinx.android.synthetic.main.recycler_view_item_forecast.view.*

class ForecastViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(requestManager: RequestManager, forecast: Forecast, index: Int) {
        view.description.text = forecast.weather_state_name
        view.temperature.text = forecast.the_temp.toString() + "°C"
        view.date.text = forecast.week_day

        val forecastIcon = forecast.weather_state_abbr

//        val options = RequestOptions()
//        options.circleCrop()

        requestManager
            .load("https://www.metaweather.com/static/img/weather/png/$forecastIcon.png")
//                .apply(options)
            .into(view.icon)
    }
}