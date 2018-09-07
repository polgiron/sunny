package com.paulgiron.supercoolapp.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.paulgiron.supercoolapp.Adapters.ForecastAdapter
import com.paulgiron.supercoolapp.R
import com.paulgiron.supercoolapp.Utilities.ServiceAPI
import kotlinx.android.synthetic.main.fragment_forecast_list.*


class ForecastListFragment : Fragment() {
    lateinit var adapter: ForecastAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forecast_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ForecastAdapter(Glide.with(this))
        recyclerViewForecastList.layoutManager = LinearLayoutManager(context)
        recyclerViewForecastList.adapter = adapter

        adapter.setData(ServiceAPI.fetchedForecastList!!)
    }
}