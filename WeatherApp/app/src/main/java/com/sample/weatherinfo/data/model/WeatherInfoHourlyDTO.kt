package com.sample.weatherinfo.data.model

import com.sample.weatherinfo.common.Constants
import com.sample.weatherinfo.domain.model.WeatherHourlyDtl

/**
WeatherInfo Hourly Data Transfer Object, received from network layer
*/


data class WeatherInfoHourlyDTO (
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherHourly>,
    val message: Int
)

data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)

data class WeatherHourly(
    val dt: Int,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val dt_txt: String
)



fun WeatherInfoHourlyDTO.toWeatherHourlyDomain() : List<WeatherHourlyDtl> {

    val hourlyList = mutableListOf<WeatherHourlyDtl>()
    list.forEach { weatherHourly->
        hourlyList.add (
            WeatherHourlyDtl(
                placeName = city.name,
                description = weatherHourly.weather[0].description,
                descriptionMain = weatherHourly.weather[0].main,
                code = cod.toInt(),
                feelsLike = convertFahrenheit(weatherHourly.main.feels_like),
                humidity = weatherHourly.main.humidity,
                temp = convertFahrenheit(weatherHourly.main.temp),
                tempMax = convertFahrenheit(weatherHourly.main.temp_max),
                tempMin = convertFahrenheit(weatherHourly.main.temp_min),
                weatherIcon = String.format(Constants.ASSET_URL, weatherHourly.weather[0].icon),
                dateTime = weatherHourly.dt_txt,
            )
        )
    }
    return hourlyList
}
