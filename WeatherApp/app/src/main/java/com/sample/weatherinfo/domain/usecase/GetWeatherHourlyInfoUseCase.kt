package com.sample.weatherinfo.domain.usecase

/**
 * UseCase Implementation to Weather List Hourly (Separation of concern)
 */

import com.sample.weatherinfo.common.Resource
import com.sample.weatherinfo.data.model.toWeatherHourlyDomain
import com.sample.weatherinfo.domain.model.WeatherDtl
import com.sample.weatherinfo.domain.model.WeatherHourlyDtl
import com.sample.weatherinfo.domain.repository.WeatherHourlyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetWeatherHourlyInfoUseCase @Inject constructor(
    private val weatherHourlyRepository: WeatherHourlyRepository
) {

    /**
     * UseCase Operator function with invoke implementation,
     * Operator Function invoke() Kotlin provides an interesting function called invoke, which is an operator function.
     * Specifying an invoke operator on a class allows it to be called on any instances of the class without a method name.
     *
     * @return Flow<Resource<List<WeatherHourlyDtl>>>
     */

    operator fun invoke(cityName: String): Flow<Resource<List<WeatherHourlyDtl>>> = flow {
        try {


            emit(Resource.Loading())
            val data = weatherHourlyRepository.getWeatherInfoHourly(cityName)
            val domainData = data.toWeatherHourlyDomain()
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