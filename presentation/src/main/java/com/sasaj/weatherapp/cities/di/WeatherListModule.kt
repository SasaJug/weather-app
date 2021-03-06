package com.sasaj.weatherapp.cities.di

import com.sasaj.domain.WeatherRepository
import com.sasaj.domain.usecases.GetCitiesUseCase
import com.sasaj.weatherapp.cities.MainVMFactory
import com.sasaj.weatherapp.common.ASyncTransformer
import com.sasaj.weatherapp.common.CityDomainToUIMapper
import dagger.Module
import dagger.Provides

@Module
class WeatherListModule {

    @Provides
    @ListScope
    fun provideGetCitiesUseCase(weatherRepository: WeatherRepository): GetCitiesUseCase {
        return GetCitiesUseCase(ASyncTransformer(), weatherRepository)
    }

    @Provides
    @ListScope
    fun provideMainVMFactory(getCitiesUseCase: GetCitiesUseCase, cityDomainToUIMapper: CityDomainToUIMapper): MainVMFactory {
        return MainVMFactory(getCitiesUseCase, cityDomainToUIMapper)
    }
}