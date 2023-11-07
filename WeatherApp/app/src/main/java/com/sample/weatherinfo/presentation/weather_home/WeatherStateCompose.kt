package com.sample.weatherinfo.presentation.weather_home

import com.sample.weatherinfo.domain.model.WeatherDtl

data class WeatherStateCompose(
    val weatherInfo: WeatherDtl? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
