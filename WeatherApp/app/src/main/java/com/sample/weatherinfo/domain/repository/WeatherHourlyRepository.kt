package com.sample.weatherinfo.domain.repository

import com.sample.weatherinfo.data.model.WeatherInfoHourlyDTO

/**
 * Repository for Get Weather List Flow.
 * Requests data from either Network or DB.
 */

interface WeatherHourlyRepository {

    /**
     * Coroutine suspend function to run this Get Weather Info Hourly for the next 3 days in background
     * @return WeatherDtl
     */

    suspend fun getWeatherInfoHourly(location: String): WeatherInfoHourlyDTO
}