package com.paulgiron.supercoolapp.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.JobIntentService
import android.support.v7.app.AppCompatActivity
import com.paulgiron.supercoolapp.Model.Forecast
import com.paulgiron.supercoolapp.R
import com.paulgiron.supercoolapp.Utilities.ServiceAPI

class SplashscreenActivity : AppCompatActivity() {
    val JOB_ID = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        ServiceAPI.listener = object : ServiceAPI.Listener {
            override fun onDownloaded() {
                startActivity(Intent(this@SplashscreenActivity, MainActivity::class.java))
                finish()
            }

            override fun onDownloadedWithData(list: List<Forecast>) {

            }
        }

        val serviceIntent = Intent().apply {
//            putExtra("download_url", dataUrl)
        }

        JobIntentService.enqueueWork(baseContext, ServiceAPI::class.java, JOB_ID, serviceIntent)
    }
}