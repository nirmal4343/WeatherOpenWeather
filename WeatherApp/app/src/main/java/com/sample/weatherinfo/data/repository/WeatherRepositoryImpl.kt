package com.sample.weatherinfo.data.repository

import com.sample.weatherinfo.data.model.WeatherDTO
import com.sample.weatherinfo.data.remote.WeatherAPI
import com.sample.weatherinfo.domain.repository.WeatherRepository

class WeatherRepositoryImpl(private val weatherAPI: WeatherAPI) : WeatherRepository {

    override suspend fun getWeatherInfoByCity(cityName: String): WeatherDTO {
        return weatherAPI.getWeatherInfoByCity("$cityName,US")
    }

    override suspend fun getWeatherInfoByZip(zipCode: String): WeatherDTO {
        return weatherAPI.getWeatherInfoByZip("$zipCode,US")
    }

    override suspend fun getWeatherInfoByLatLong(latitude: Double, longitude: Double): WeatherDTO {
        return weatherAPI.getWeatherInfoByLatLong(latitude, longitude)
    }

}