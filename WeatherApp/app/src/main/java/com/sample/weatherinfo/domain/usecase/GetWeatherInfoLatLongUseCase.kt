package com.sample.weatherinfo.domain.usecase

/**
 * UseCase Implementation to Get Weather List (Separation of concern)
 */

import com.sample.weatherinfo.common.Resource
import com.sample.weatherinfo.data.model.toWeatherDomain
import com.sample.weatherinfo.domain.model.WeatherDtl
import com.sample.weatherinfo.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetWeatherInfoLatLongUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    /**
     * UseCase Operator function with invoke implementation,
     * Operator Function invoke() Kotlin provides an interesting function called invoke, which is an operator function.
     * Specifying an invoke operator on a class allows it to be called on any instances of the class without a method name.
     *
     * @return Flow<Resource<List<WeatherDtl>>>
     */

    operator fun invoke(latitude: Double, longitude: Double): Flow<Resource<WeatherDtl>> = flow {
        try {

            emit(Resource.Loading())
            val data = weatherRepository.getWeatherInfoByLatLong(latitude, longitude)
            val domainData = data.toWeatherDomain()
            emit(Resource.Success(data = domainData))

        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An Unknown error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Check Connectivity"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: "Some Error"))
        }
    }

}