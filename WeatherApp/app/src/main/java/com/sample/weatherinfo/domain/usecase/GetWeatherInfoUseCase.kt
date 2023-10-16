package com.sample.weatherinfo.domain.usecase

/**
 * UseCase Implementation to GetWeather List (Separation of concern)
 */

import androidx.core.text.isDigitsOnly
import com.sample.weatherinfo.common.Resource
import com.sample.weatherinfo.data.model.WeatherDTO
import com.sample.weatherinfo.data.model.toWeatherDomain
import com.sample.weatherinfo.domain.model.WeatherDtl
import com.sample.weatherinfo.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetWeatherInfoUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    /**
     * UseCase Operator function with invoke implementation,
     * Operator Function invoke() Kotlin provides an interesting function called invoke, which is an operator function.
     * Specifying an invoke operator on a class allows it to be called on any instances of the class without a method name.
     *
     * @return Flow<Resource<List<WeatherDetails>>>
     */

    operator fun invoke(location : String): Flow<Resource<WeatherDtl>> = flow {
        try {

            emit(Resource.Loading())
            val data : WeatherDTO = if(location.isDigitsOnly()) {
                weatherRepository.getWeatherInfoByZip(location)
            } else {
                weatherRepository.getWeatherInfoByCity(location)
            }
            val domainData = data.toWeatherDomain()
            emit(Resource.Success(data = domainData))

        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An Unknown error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Check Connectivity"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: "Error"))
        }
    }


}