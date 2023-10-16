package com.sample.weatherinfo.domain.model



/**
 * The domain object class (Weather Hourly Details), used to present data at the ViewModel and UI coordination.
 */

data class WeatherHourlyDtl(
    val placeName: String ,
    val description : String ,
    val descriptionMain : String ,
    val code: Int,
    val feelsLike: Int = 0,
    val humidity: Int = 0,
    val temp: Int = 0,
    val tempMax: Int = 0,
    val tempMin: Int = 0,
    val weatherIcon : String,
    val dateTime : String
)
