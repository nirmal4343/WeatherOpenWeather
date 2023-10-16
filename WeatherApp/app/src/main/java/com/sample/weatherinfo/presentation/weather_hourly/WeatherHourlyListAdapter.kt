package com.sample.weatherinfo.presentation.weather_hourly

/**
 * Adapter for RecyclerViews (To Display the Weather Hourly List).
 *
 */

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.weather.databinding.ViewHolderWeatherListBinding
import com.sample.weatherinfo.domain.model.WeatherHourlyDtl

class WeatherHourlyListAdapter : RecyclerView.Adapter<WeatherHourlyListAdapter.WeatherInfoViewHolder>() {

    var list = mutableListOf<WeatherHourlyDtl>()

    fun setContentList(list: MutableList<WeatherHourlyDtl>) {
        // Received new weather info data, refreshing the list accordingly
        this.list = list
        notifyDataSetChanged()
    }


    class WeatherInfoViewHolder(val viewHolder: ViewHolderWeatherListBinding) :
        RecyclerView.ViewHolder(viewHolder.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherInfoViewHolder {
        val binding =
            ViewHolderWeatherListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherInfoViewHolder(binding)
    }

    // Binding the Weather Object to the view (list view cell) for each row
    override fun onBindViewHolder(holder: WeatherInfoViewHolder, position: Int) {
        holder.viewHolder.weather = this.list[position]
    }

    // Total Item Count (Weather count)
    override fun getItemCount(): Int {
        return this.list.size
    }
}