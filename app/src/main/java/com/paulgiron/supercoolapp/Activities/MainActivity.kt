package com.paulgiron.supercoolapp.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.paulgiron.supercoolapp.Fragments.ForecastListFragment
import com.paulgiron.supercoolapp.R
import com.paulgiron.supercoolapp.Utilities.ServiceAPI
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        city.text = ServiceAPI.city
        country.text = ServiceAPI.country

        val transaction = supportFragmentManager.beginTransaction()
        transaction.let {
            transaction.add(R.id.frameLayout, ForecastListFragment(), "ForecastListFragment")
            transaction.commit()
        }
    }
}
