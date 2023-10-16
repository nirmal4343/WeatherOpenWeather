package com.sample.weatherinfo.data.repository

import com.sample.weatherinfo.data.model.WeatherDTO
import com.sample.weatherinfo.data.model.WeatherInfoHourlyDTO
import com.sample.weatherinfo.data.remote.WeatherAPI
import com.sample.weatherinfo.domain.repository.WeatherHourlyRepository

class WeatherHourlyRepositoryImpl(private val weatherAPI: WeatherAPI) : WeatherHourlyRepository {


    override suspend fun getWeatherInfoHourly(city: String): WeatherInfoHourlyDTO {
        return weatherAPI.getWeatherInfoHourly("$city,US")
    }

}