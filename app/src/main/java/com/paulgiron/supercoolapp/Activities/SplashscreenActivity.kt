package com.paulgiron.supercoolapp.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.JobIntentService
import android.support.v7.app.AppCompatActivity
import com.paulgiron.supercoolapp.Model.Forecast
import com.paulgiron.supercoolapp.R
import com.paulgiron.supercoolapp.Utilities.DownloadForecastAPI

class SplashscreenActivity : AppCompatActivity() {
    val JOB_ID = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        DownloadForecastAPI.listener = object : DownloadForecastAPI.Listener {
            override fun onDownloaded() {
                startActivity(Intent(this@SplashscreenActivity, MainActivity::class.java))
                finish()
            }

            override fun onDownloadedWithData(list: List<Forecast>) {

            }
        }

        val serviceIntent = Intent().apply {
            putExtra("woeid", 44418)
        }

        JobIntentService.enqueueWork(baseContext, DownloadForecastAPI::class.java, JOB_ID, serviceIntent)
    }
}