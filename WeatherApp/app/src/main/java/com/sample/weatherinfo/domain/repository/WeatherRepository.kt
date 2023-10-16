package com.sample.weatherinfo.domain.repository

import com.sample.weatherinfo.data.model.WeatherDTO

/**
 * Repository for Get Weather List Flow.
 * Requests data from either Network or DB.
 */

interface WeatherRepository {

    /**
     * Coroutine suspend function to run this Get Weather Info by City in background
     * @return WeatherDtl
     */
    suspend fun getWeatherInfoByCity(cityName: String): WeatherDTO

    /**
     * Coroutine suspend function to run this Get Weather Info by City in background
     * @return WeatherDtl
     */
    suspend fun getWeatherInfoByZip(zipCode: String): WeatherDTO

    /**
     * Coroutine suspend function to run this Get Weather Info by Lat Long in background
     * @return WeatherDtl
     */
    suspend fun getWeatherInfoByLatLong(latitude: Double, longitude: Double): WeatherDTO

}