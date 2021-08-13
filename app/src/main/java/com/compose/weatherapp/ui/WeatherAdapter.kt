package com.compose.weatherapp.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.compose.weatherapp.data.database.WeatherReport
import com.compose.weatherapp.databinding.LayoutWeatherBinding
import com.compose.weatherapp.utils.Utils

class WeatherAdapter : ListAdapter<WeatherReport, WeatherAdapter.MyViewHolder>(WeatherDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(LayoutWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }


    inner class MyViewHolder(private val view: LayoutWeatherBinding) : RecyclerView.ViewHolder(view.root){

        @SuppressLint("SetTextI18n")
        fun bindData(item: WeatherReport?) {
            view.tvHumidity.text = "${item?.humidity} %"
            view.tvTemperature.text = "${item?.temperature}° C"
            view.tvTime.text = Utils.getDate(item?.time!!, "hh:mm:ss a")

        }


    }

}

class WeatherDiff : DiffUtil.ItemCallback<WeatherReport>(){
    override fun areItemsTheSame(oldItem: WeatherReport, newItem: WeatherReport): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: WeatherReport, newItem: WeatherReport): Boolean =
        oldItem == newItem

}