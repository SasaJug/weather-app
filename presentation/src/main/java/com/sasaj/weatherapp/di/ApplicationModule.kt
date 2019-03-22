package com.sasaj.weatherapp.di

import android.content.Context
import android.net.ConnectivityManager
import com.sasaj.data.LocalRepository
import com.sasaj.data.RemoteRepository
import com.sasaj.data.WeatherRepositoryImpl
import com.sasaj.data.common.LocationDtoToDomainMapper
import com.sasaj.data.httpclient.RetrofitClient
import com.sasaj.data.mappers.CityDtoToDomainMapper
import com.sasaj.domain.NetworkManager
import com.sasaj.domain.WeatherRepository
import com.sasaj.weatherapp.common.NetworkManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun provideNetworkManager(connectivityManager: ConnectivityManager): NetworkManager {
        return NetworkManagerImpl(connectivityManager)
    }

    @Provides
    @Singleton
    fun provideCityDtoToDomainMapper(): CityDtoToDomainMapper {
        return  CityDtoToDomainMapper()
    }

    @Provides
    @Singleton
    fun provideLocationDtoToDomainMapper(): LocationDtoToDomainMapper {
        return  LocationDtoToDomainMapper()
    }

    @Provides
    @Singleton
    fun provideHttpClient(): RetrofitClient {
        return  RetrofitClient()
    }

    @Provides
    @Singleton
    fun provideLocalRepository(cityDtoToDomainMapper : CityDtoToDomainMapper): LocalRepository {
        return  LocalRepository(context, cityDtoToDomainMapper)
    }

    @Provides
    @Singleton
    fun provideRemoteRepository(httpClient : RetrofitClient, locationDtoToDomainMapper: LocationDtoToDomainMapper): RemoteRepository {
        return  RemoteRepository(httpClient, locationDtoToDomainMapper)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(localRepository: LocalRepository, remoteRepository: RemoteRepository): WeatherRepository{
        return  WeatherRepositoryImpl(localRepository, remoteRepository)
    }
}