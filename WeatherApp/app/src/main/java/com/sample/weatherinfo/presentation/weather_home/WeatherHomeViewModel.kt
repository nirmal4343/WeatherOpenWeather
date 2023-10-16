package com.sample.weatherinfo.presentation.weather_home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.weatherinfo.common.Constants.LOCATION_NAME
import com.sample.weatherinfo.common.Constants.SHARED_PREF_LOCATION
import com.sample.weatherinfo.common.Resource
import com.sample.weatherinfo.domain.usecase.GetWeatherInfoLatLongUseCase
import com.sample.weatherinfo.domain.usecase.GetWeatherInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * WeatherHomeViewModel, interacts with Repository layer via UseCase.
 */


@HiltViewModel
class WeatherHomeViewModel @Inject constructor(
    private val getWeatherInfoUseCase: GetWeatherInfoUseCase,
    private val getWeatherInfoLatLongUseCase: GetWeatherInfoLatLongUseCase
) : ViewModel() {

    private val _weatherInfo = MutableStateFlow(WeatherState())
    val weatherInfo: StateFlow<WeatherState> = _weatherInfo

    /**
     * Get  Weather Info by City or Zip Code from Open Weather database
     * Call invoked in ViewModel scope
     */

    fun getWeatherInfo(location : String) {
        getWeatherInfoUseCase(location).onEach {
            when (it) {
                is Resource.Loading -> {
                    _weatherInfo.value = WeatherState(isLoading = true)
                }

                is Resource.Success -> {
                    _weatherInfo.value = WeatherState(data = it.data)
                }

                is Resource.Error -> {
                    _weatherInfo.value = WeatherState(error = "No Record Found")
                }
            }
        }.launchIn(viewModelScope)
    }


    /**
     * Get  Weather Info by Lat-Long from Open Weather database
     * Call invoked in ViewModel scope
     */

    fun getWeatherInfoByLatLong(latitude: Double, longitude: Double) {
        getWeatherInfoLatLongUseCase(latitude, longitude).onEach {
            when (it) {
                is Resource.Loading -> {
                    _weatherInfo.value = WeatherState(isLoading = true)
                }

                is Resource.Success -> {
                    _weatherInfo.value = WeatherState(data = it.data)
                }

                is Resource.Error -> {
                    _weatherInfo.value = WeatherState(error = "No Record Found")
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Get City Name to local shared-pref
     */

    fun getLocalStoredLocation(context: Context?) : String? {
        return context?.let {
            val pref = it.getSharedPreferences(SHARED_PREF_LOCATION, Context.MODE_PRIVATE);
            pref.getString(LOCATION_NAME, null);
        }
    }


    /**
     * Set City Name to local shared-pref
     */

    fun setLocalStoredLocation(context: Context?, location: String) {
        context?.let {
            val pref = it.getSharedPreferences(SHARED_PREF_LOCATION, Context.MODE_PRIVATE);
            val editor =  pref.edit()

            editor.apply {
                putString(LOCATION_NAME,location)
                apply()
            }
        }
    }
}