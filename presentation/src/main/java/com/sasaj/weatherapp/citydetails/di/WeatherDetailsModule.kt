package com.sasaj.weatherapp.citydetails.di

import com.sasaj.domain.WeatherRepository
import com.sasaj.domain.usecases.GetWeatherForCityUseCase
import com.sasaj.weatherapp.cities.di.ListScope
import com.sasaj.weatherapp.citydetails.DetailsVMFactory
import com.sasaj.weatherapp.common.ASyncTransformer
import dagger.Module
import dagger.Provides

@Module
class WeatherDetailsModule {

    @Provides
    @DetailsScope
    fun provideGetWeatherForCityIdUseCase(weatherRepository: WeatherRepository): GetWeatherForCityUseCase {
        return GetWeatherForCityUseCase(ASyncTransformer(), weatherRepository)
    }

    @Provides
    @DetailsScope
    fun provideMainVMFactory(getWeatherForCityUseCase: GetWeatherForCityUseCase): DetailsVMFactory {
        return DetailsVMFactory(getWeatherForCityUseCase)
    }
}