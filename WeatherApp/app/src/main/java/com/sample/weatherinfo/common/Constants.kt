package com.sample.weatherinfo.common

import com.sample.weatherinfo.data.model.WeatherDTO
import com.sample.weatherinfo.data.model.WeatherInfoHourlyDTO
import retrofit2.http.GET
import retrofit2.http.Query

object Constants {

    const val BASE_URL = "https://api.openweathermap.org/"

    const val API_KEY = "dcacc888391dd5b76fd9c490207f2a45"

    const val ASSET_URL = "https://openweathermap.org/img/wn/%s@2x.png"

    const val SHARED_PREF_LOCATION = "localStorageLocation"

    const val LOCATION_NAME = "locationName"

    const val CONTEXT_URL_WEATHER = "/data/2.5/weather"

    const val CONTEXT_URL_FORECAST = "/data/2.5/forecast"

    const val QUERY_PARAM_Q = "q"

    const val QUERY_PARAM_ZIP = "zip"

    const val QUERY_PARAM_LAT = "lat"

    const val QUERY_PARAM_LONG = "lon"

    const val QUERY_PARAM_APP_ID = "appid"

}