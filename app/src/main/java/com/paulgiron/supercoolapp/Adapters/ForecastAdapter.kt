package com.paulgiron.supercoolapp.Adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.paulgiron.supercoolapp.Model.Forecast
import com.paulgiron.supercoolapp.R
import com.paulgiron.supercoolapp.ViewHolders.ForecastViewHolder
import com.paulgiron.supercoolapp.inflate

class ForecastAdapter(val requestManager: RequestManager) : RecyclerView.Adapter<ForecastViewHolder>() {
    private var forecastList: ArrayList<Forecast> = ArrayList()

    fun setData(dataList: ArrayList<Forecast>) {
        forecastList.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = parent.inflate(R.layout.recycler_view_item_forecast)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(requestManager, forecastList[position], position)
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }
}