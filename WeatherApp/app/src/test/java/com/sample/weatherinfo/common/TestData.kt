package com.sample.weatherinfo.common

import com.sample.weatherinfo.domain.model.WeatherDtl
import com.sample.weatherinfo.domain.model.WeatherHourlyDtl


fun getDummyWeather() =
    WeatherDtl(
        "Atlanta",
        "Cloudy",
        "Broken Cloudy",
        200,
        56,
        44,
        67,
        79,
        60,
        "01d"
    )


fun getWeatherHourlyList() = listOf(
    WeatherHourlyDtl(
        "Atlanta",
        "Cloudy",
        "Broken Cloudy",
        200,
        56,
        44,
        67,
        79,
        60,
        "01d",
        dateTime = "2023-10-15 03:00:00"
    )
)
