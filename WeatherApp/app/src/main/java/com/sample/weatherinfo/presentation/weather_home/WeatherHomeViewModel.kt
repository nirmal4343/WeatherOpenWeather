package com.sample.weatherinfo.presentation.weather_home

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.weatherinfo.common.Constants.LOCATION_NAME
import com.sample.weatherinfo.common.Constants.SHARED_PREF_LOCATION
import com.sample.weatherinfo.common.LocationTracker
import com.sample.weatherinfo.common.Resource
import com.sample.weatherinfo.domain.usecase.GetWeatherInfoLatLongUseCase
import com.sample.weatherinfo.domain.usecase.GetWeatherInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * WeatherHomeViewModel, interacts with Repository layer via UseCase.
 */


@HiltViewModel
class WeatherHomeViewModel @Inject constructor(
    private val getWeatherInfoUseCase: GetWeatherInfoUseCase,
    private val getWeatherInfoLatLongUseCase: GetWeatherInfoLatLongUseCase,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _weatherInfo = MutableStateFlow(WeatherState())
    val weatherInfo: StateFlow<WeatherState> = _weatherInfo

    var state by mutableStateOf(WeatherStateCompose())
        private set

    /**
     * Get  Weather Info by City or Zip Code from Open Weather database
     * Call invoked in ViewModel scope
     */

    fun getWeatherInfoByLatLong() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->

                getWeatherInfoLatLongUseCase(location.latitude, location.longitude).onEach {
                    when (it) {
                        is Resource.Loading -> {
                            _weatherInfo.value = WeatherState(isLoading = true)
                        }

                        is Resource.Success -> {
                            _weatherInfo.value = WeatherState(data = it.data)
                            state = state.copy(
                                weatherInfo = it.data,
                                isLoading = false,
                                error = null
                            )
                        }

                        is Resource.Error -> {
                            _weatherInfo.value = WeatherState(error = "No Record Found")
                            state = state.copy(
                                weatherInfo = null,
                                isLoading = false,
                                error = it.message
                            )
                        }
                    }
                }.launchIn(viewModelScope)
            } ?: kotlin.run {
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                )
            }
        }
    }

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

        state = state.copy(
            isLoading = true,
            error = null
        )

        getWeatherInfoLatLongUseCase(latitude, longitude).onEach {
            when (it) {
                is Resource.Loading -> {
                    _weatherInfo.value = WeatherState(isLoading = true)
                }

                is Resource.Success -> {
                    _weatherInfo.value = WeatherState(data = it.data)
                    state = state.copy(
                        weatherInfo = it.data,
                        isLoading = false,
                        error = null
                    )
                }

                is Resource.Error -> {
                    _weatherInfo.value = WeatherState(error = "No Record Found")
                    state = state.copy(
                        weatherInfo = null,
                        isLoading = false,
                        error = it.message
                    )
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