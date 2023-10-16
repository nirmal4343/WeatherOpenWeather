package com.sample.weatherinfo.data.model

import com.sample.weatherinfo.common.Constants
import com.sample.weatherinfo.domain.model.WeatherDtl

/**
 WeatherInfo Data Transfer Object, received from network layer
*/


data class WeatherDTO(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)

data class Clouds(
    val all: Int
)

data class Coord(
    val lat: Double,
    val lon: Double
)

data class Main(
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)

data class Sys(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class Wind(
    val deg: Int,
    val speed: Double
)

// Convert DTO object to the object accessible in Domain layer for further processing at UI layer

fun WeatherDTO.toWeatherDomain() : WeatherDtl {
    return WeatherDtl (
        placeName = name,
        description = weather[0].description,
        descriptionMain = weather[0].main,
        code = cod,
        feelsLike = convertFahrenheit(main.feels_like),
        humidity  = main.humidity,
        temp = convertFahrenheit(main.temp),
        tempMax = convertFahrenheit(main.temp_max),
        tempMin = convertFahrenheit(main.temp_min),
        weatherIcon = String.format(Constants.ASSET_URL, weather[0].icon)
    )
}

fun convertFahrenheit(temperature : Double) : Int {

    return  ((temperature * 9/5) - 459.67).toInt();
}