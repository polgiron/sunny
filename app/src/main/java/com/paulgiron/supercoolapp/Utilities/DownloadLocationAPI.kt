package com.paulgiron.supercoolapp.Utilities

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.support.v4.app.JobIntentService
import android.util.Log
import com.paulgiron.supercoolapp.Model.CurrentLocation
import com.paulgiron.supercoolapp.Model.Forecast
import com.paulgiron.supercoolapp.R.id.country
import com.paulgiron.supercoolapp.Utilities.DownloadForecastAPI.Companion.fetchedForecastList
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class DownloadLocationAPI : JobIntentService() {
    val client = OkHttpClient()
    val JOB_ID = 1000

    companion object {
        var listener: Listener? = null
        var city: String? = null
    }

    interface Listener {
        fun onDownloaded()
        fun onDownloadedWithData(list: List<Forecast>)
    }

    override fun onHandleWork(workIntent: Intent) {
        val latitude = workIntent.getStringExtra("latitude")
        val longitude = workIntent.getStringExtra("longitude")
        downloadData(latitude, longitude)
    }

    private fun downloadData(latitude: String?, longitude: String?) {
        val request = Request.Builder()
            .url("https://www.metaweather.com/api/location/search/?lattlong=$latitude,$longitude")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i("DEV", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.i("DEV", "HTTP RESPONSE")

                val dataString: String? = response.body()?.string()
                val locationArray = JSONArray(dataString)
                val jsonObject = locationArray.getJSONObject(0)
                var currentLocation: CurrentLocation = CurrentLocation(
                    jsonObject.getInt("distance"),
                    jsonObject.getString("title"),
                    jsonObject.getString("location_type"),
                    jsonObject.getInt("woeid"),
                    jsonObject.getString("latt_long")
                )

//                Log.i("DEV", locationArray[0].toString())

                val serviceIntent = Intent().apply {
                    putExtra("woeid", currentLocation.woeid)
                }

                JobIntentService.enqueueWork(baseContext, DownloadForecastAPI::class.java, JOB_ID, serviceIntent)

                listener?.onDownloaded()
            }
        })
    }
}