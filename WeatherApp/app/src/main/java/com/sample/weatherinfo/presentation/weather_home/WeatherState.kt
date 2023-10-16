package com.sample.weatherinfo.presentation.weather_home



/**
 * Data class with the possible State wile processing api response (for Weather API call)
 */


import com.sample.weatherinfo.domain.model.WeatherDtl

data class WeatherState (
    val isLoading: Boolean = false,
    val data: WeatherDtl? = null,
    val error: String = ""
)