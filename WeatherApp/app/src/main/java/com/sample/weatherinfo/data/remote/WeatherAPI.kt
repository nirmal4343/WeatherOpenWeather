package com.sample.weatherinfo.data.remote

import com.sample.weatherinfo.common.Constants
import com.sample.weatherinfo.common.Constants.CONTEXT_URL_FORECAST
import com.sample.weatherinfo.common.Constants.CONTEXT_URL_WEATHER
import com.sample.weatherinfo.common.Constants.QUERY_PARAM_APP_ID
import com.sample.weatherinfo.common.Constants.QUERY_PARAM_LAT
import com.sample.weatherinfo.common.Constants.QUERY_PARAM_LONG
import com.sample.weatherinfo.common.Constants.QUERY_PARAM_Q
import com.sample.weatherinfo.common.Constants.QUERY_PARAM_ZIP
import com.sample.weatherinfo.data.model.WeatherDTO
import com.sample.weatherinfo.data.model.WeatherInfoHourlyDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    /**
     * Get the Weather Info by City, Zip and Lat Long
     * @return WeatherDTO
     */

    @GET(CONTEXT_URL_WEATHER)
    suspend fun getWeatherInfoByCity(
        @Query(QUERY_PARAM_Q) city : String,
        @Query(QUERY_PARAM_APP_ID) appId : String = Constants.API_KEY
    ) : WeatherDTO

    @GET(CONTEXT_URL_WEATHER)
    suspend fun getWeatherInfoByZip(
        @Query(QUERY_PARAM_ZIP) zip : String,
        @Query(QUERY_PARAM_APP_ID) appId : String = Constants.API_KEY
    ) : WeatherDTO

    @GET(CONTEXT_URL_WEATHER)
    suspend fun getWeatherInfoByLatLong(
        @Query(QUERY_PARAM_LAT) lat : Double,
        @Query(QUERY_PARAM_LONG) long : Double,
        @Query(QUERY_PARAM_APP_ID) appId : String = Constants.API_KEY
    ) : WeatherDTO

    @GET(CONTEXT_URL_FORECAST)
    suspend fun getWeatherInfoHourly(
        @Query(QUERY_PARAM_Q) city : String,
        @Query(QUERY_PARAM_APP_ID) appId : String = Constants.API_KEY
    ) : WeatherInfoHourlyDTO

}