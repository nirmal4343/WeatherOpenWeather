package com.sample.weatherinfo.presentation.weather_hourly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.weatherinfo.common.Resource
import com.sample.weatherinfo.domain.usecase.GetWeatherHourlyInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * WeatherHourlyViewModel, interacts with Repository layer via UseCase.
 */


@HiltViewModel
class WeatherHourlyViewModel @Inject constructor(
    private val getWeatherHourlyInfoUseCase: GetWeatherHourlyInfoUseCase
) : ViewModel() {

    private val _weatherHourlyList = MutableStateFlow(WeatherHourlyListState())
    val weatherHourlyList: StateFlow<WeatherHourlyListState> = _weatherHourlyList

    /**
     * Get List of WeatherInfo from Open Weather database, for the next 3 days hourly
     * Call invoked in ViewModel scope
     *
     */

    fun getWeatherInfoHourly(cityName : String) {
        getWeatherHourlyInfoUseCase(cityName).onEach {
            when (it) {
                is Resource.Loading -> {
                    //Show loading
                    _weatherHourlyList.value = WeatherHourlyListState(isLoading = true)
                }
                is Resource.Success -> {
                    // Received the Weather Info list successfully for the next 3 days hourly
                    _weatherHourlyList.value = WeatherHourlyListState(data = it.data)
                }
                is Resource.Error -> {
                    // Handle error
                    _weatherHourlyList.value = WeatherHourlyListState(error = it.message ?: "")
                }
            }
        }.launchIn(viewModelScope)
    }
}