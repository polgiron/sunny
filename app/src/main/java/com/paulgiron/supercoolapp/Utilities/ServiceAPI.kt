package com.paulgiron.supercoolapp.Utilities

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.support.v4.app.JobIntentService
import android.util.Log
import com.paulgiron.supercoolapp.Model.Forecast
import com.paulgiron.supercoolapp.R.id.country
import okhttp3.*
import java.io.IOException

class ServiceAPI : JobIntentService() {
    val client = OkHttpClient()

    companion object {
        var listener: Listener? = null
        var fetchedForecastList: ArrayList<Forecast>? = null
        var city: String? = null
        var country: String? = null
    }

    interface Listener {
        fun onDownloaded()
        fun onDownloadedWithData(list: List<Forecast>)
    }

    override fun onHandleWork(p0: Intent) {
        downloadData()
    }

    private fun downloadData() {
        val request = Request.Builder()
//                .url("https://www.metaweather.com/api/location/2459115/2018/9/5/")
//            .url("https://www.metaweather.com/api/location/2459115")
            .url("https://www.metaweather.com/api/location/44418")
            .build()
//        val handler = Handler(Looper.getMainLooper())

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
//                Log.i("TEST", "HTTP FAILURE")
                Log.i("TEST", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
//                Log.i("TEST", "HTTP RESPONSE")

                val dataString: String? = response.body()?.string()

                val parseForecast = ParseForecastUtility()
                fetchedForecastList = parseForecast.parseForecast(dataString)
                city = parseForecast.parseCity(dataString)
                country = parseForecast.parseCountry(dataString)

                parseForecast.parseCity(dataString)

//                Log.i("TEST", forecastList.toString())

//                handler.post(Runnable {
//                    adapter.setData(forecastList)
//                })

                listener?.onDownloaded()
            }
        })
    }
}