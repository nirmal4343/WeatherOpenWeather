package com.sample.weatherinfo.presentation.weather_hourly



/**
 * Data class with the possible State wile processing api response (for Hourly Weather list API call)
 */

import com.sample.weatherinfo.domain.model.WeatherHourlyDtl

data class WeatherHourlyListState (
    val isLoading: Boolean = false,
    val data: List<WeatherHourlyDtl>? = null,
    val error: String = ""
)