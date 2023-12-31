package com.sample.weatherinfo.hilt

import com.sample.weatherinfo.common.AddLoggingInterceptor
import com.sample.weatherinfo.common.Constants
import com.sample.weatherinfo.data.remote.WeatherAPI
import com.sample.weatherinfo.data.repository.WeatherHourlyRepositoryImpl
import com.sample.weatherinfo.data.repository.WeatherRepositoryImpl
import com.sample.weatherinfo.domain.repository.WeatherHourlyRepository
import com.sample.weatherinfo.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object HIltModules {

    @Provides
    @Singleton
    fun provideWeatherAPI(): WeatherAPI {
        // Create Retrofit instance
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(AddLoggingInterceptor.setLogging())
            .build()
            .create(WeatherAPI::class.java)
    }

    // Create Weather Repository, that can be injected as constructor using Hilt Dependency Injection
    @Provides
    fun provideWeatherRepository(weatherAPI: WeatherAPI): WeatherRepository {
        return WeatherRepositoryImpl(weatherAPI)
    }


    // Create WeatherHourly Repository, that can be injected as constructor using Hilt Dependency Injection
    @Provides
    fun provideWeatherHourlyRepository(weatherAPI: WeatherAPI): WeatherHourlyRepository {
        return WeatherHourlyRepositoryImpl(weatherAPI)
    }

}