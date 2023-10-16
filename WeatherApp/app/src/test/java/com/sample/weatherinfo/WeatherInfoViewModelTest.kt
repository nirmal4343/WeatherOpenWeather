package com.sample.weatherinfo


import com.sample.weatherinfo.common.getDummyWeather
import com.weatherinfo.BaseViewModelTest
import com.sample.weatherinfo.common.Resource
import com.sample.weatherinfo.domain.usecase.GetWeatherInfoLatLongUseCase
import com.sample.weatherinfo.domain.usecase.GetWeatherInfoUseCase
import com.weatherinfo.runBlockingMainTest
import com.sample.weatherinfo.presentation.weather_home.WeatherHomeViewModel
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
internal class WeatherInfoViewModelTest : BaseViewModelTest(){

    @Mock
    private lateinit var getWeatherInfoUseCase: GetWeatherInfoUseCase
    @Mock
    private lateinit var getWeatherInfoLatLongUseCase: GetWeatherInfoLatLongUseCase

    private lateinit var weatherHomeViewModel: WeatherHomeViewModel

    @Before
    fun setUp() {
        weatherHomeViewModel = WeatherHomeViewModel(
            getWeatherInfoUseCase,
            getWeatherInfoLatLongUseCase
        )
    }

    @Test
    fun `Given Characters when fetchWeather by Lat Long should return Success`() = runBlockingMainTest {
        //GIVEN
        val flowQuestions = flowOf(Resource.Success(getDummyWeather()))

        //WHEN
        Mockito.doReturn(flowQuestions).`when`(getWeatherInfoLatLongUseCase).invoke(22.3,33.4)
        weatherHomeViewModel.getWeatherInfoByLatLong(22.3,33.4)

        //THEN

        assert("Atlanta" == weatherHomeViewModel.weatherInfo.value.data?.placeName)
        assert("01d" == weatherHomeViewModel.weatherInfo.value.data?.weatherIcon)
        assert(67 == weatherHomeViewModel.weatherInfo.value.data?.temp)
        assert(60 == weatherHomeViewModel.weatherInfo.value.data?.tempMin)
    }
}