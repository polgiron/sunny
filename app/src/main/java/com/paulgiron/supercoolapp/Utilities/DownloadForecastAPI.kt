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

class DownloadForecastAPI : JobIntentService() {
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

    override fun onHandleWork(workIntent: Intent) {
        val woeid: Int = workIntent.getIntExtra("woeid", 44418)
        downloadData(woeid)
    }

    private fun downloadData(woeid: Int) {
        val request = Request.Builder()
            .url("https://www.metaweather.com/api/location/$woeid")
//            .url("https://www.metaweather.com/api/location/44418")
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