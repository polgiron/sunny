package com.paulgiron.supercoolapp.Activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.JobIntentService
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.paulgiron.supercoolapp.Fragments.ForecastListFragment
import com.paulgiron.supercoolapp.R
import com.paulgiron.supercoolapp.Utilities.DownloadForecastAPI
import com.paulgiron.supercoolapp.Utilities.DownloadLocationAPI
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
//    private var locationManager : LocationManager? = null
    private val PERMISSIONS_REQUEST_LOCATION = 1
    private val DOWNLOAD_LOCATION_JOB_ID = 2
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val fragment = ForecastListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        city.text = DownloadForecastAPI.city
        country.text = DownloadForecastAPI.country

        val transaction = supportFragmentManager.beginTransaction()
        transaction.let {
            transaction.add(R.id.frameLayout, fragment, "ForecastListFragment")
            transaction.commit()
        }

//        Location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?

        locationButton.setOnClickListener { view ->
            locationButton.setVisibility(View.GONE)
            locationProgressBar.setVisibility(View.VISIBLE)
            askLocationPermission()
        }
    }

    private fun onLocationClick() {
//        try {
//            // Request location updates
//            locationManager?.requestLocationUpdates(
//                    LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
//        } catch(ex: SecurityException) {
//            Log.d("DEV", "Security Exception, no location available");
//        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
            // Got last known location. In some rare situations this can be null.
            Log.i("DEV", location?.longitude.toString())
            Log.i("DEV", location?.latitude.toString())

            val serviceIntent = Intent().apply {
                putExtra("latitude", location?.latitude.toString())
                putExtra("longitude", location?.longitude.toString())
            }

            JobIntentService.enqueueWork(baseContext,
                    DownloadLocationAPI::class.java, DOWNLOAD_LOCATION_JOB_ID, serviceIntent)
        }
    }

    private fun askLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.i("DEV", "Permission is not granted")
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        PERMISSIONS_REQUEST_LOCATION)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            onLocationClick()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay!
                    onLocationClick()
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        locationButton.setVisibility(View.VISIBLE)
        locationProgressBar.setVisibility(View.GONE)
        city.text = DownloadForecastAPI.city
        country.text = DownloadForecastAPI.country
        fragment.updateList()
    }

//    private val locationListener: LocationListener = object : LocationListener {
//        override fun onLocationChanged(location: Location) {
////            Log.i("DEV", location.longitude.toString())
////            Log.i("DEV", location.latitude.toString())
//        }
//        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
//        override fun onProviderEnabled(provider: String) {}
//        override fun onProviderDisabled(provider: String) {}
//    }
}
