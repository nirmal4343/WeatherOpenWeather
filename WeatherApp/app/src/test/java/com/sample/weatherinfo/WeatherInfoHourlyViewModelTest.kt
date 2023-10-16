package com.sample.weatherinfo


import com.sample.weatherinfo.common.getWeatherHourlyList
import com.weatherinfo.BaseViewModelTest
import com.sample.weatherinfo.common.Resource
import com.sample.weatherinfo.domain.usecase.GetWeatherHourlyInfoUseCase
import com.sample.weatherinfo.presentation.weather_hourly.WeatherHourlyViewModel
import com.weatherinfo.runBlockingMainTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class WeatherInfoHourlyViewModelTest : BaseViewModelTest(){

    @Mock
    private lateinit var getWeatherHourlyInfoUseCase: GetWeatherHourlyInfoUseCase

    private lateinit var weatherHourlyViewModel: WeatherHourlyViewModel

    @Before
    fun setUp() {
        weatherHourlyViewModel = WeatherHourlyViewModel(getWeatherHourlyInfoUseCase)
    }

    @Test
    fun `Given Characters when fetchWeather By City Name should return Success`() = runBlockingMainTest {
        //GIVEN
        val flowQuestions = flowOf(Resource.Success(getWeatherHourlyList()))

        //WHEN
        Mockito.doReturn(flowQuestions).`when`(getWeatherHourlyInfoUseCase).invoke("Atlanta")
        weatherHourlyViewModel.getWeatherInfoHourly("Atlanta")


        assert(1 == weatherHourlyViewModel.weatherHourlyList.value.data?.size)
        assert("Atlanta" == weatherHourlyViewModel.weatherHourlyList.value.data?.get(0)?.placeName)
        assert("01d" == weatherHourlyViewModel.weatherHourlyList.value.data?.get(0)?.weatherIcon)
        assert(67 == weatherHourlyViewModel.weatherHourlyList.value.data?.get(0)?.temp)
        assert(60 == weatherHourlyViewModel.weatherHourlyList.value.data?.get(0)?.tempMin)

    }
}