package com.sasaj.weatherapp.di

import android.content.Context
import android.net.ConnectivityManager
import com.sasaj.data.LocalRepository
import com.sasaj.data.RemoteRepository
import com.sasaj.data.WeatherRepositoryImpl
import com.sasaj.data.common.LocationDtoToDomainMapper
import com.sasaj.data.httpclient.OWMService
import com.sasaj.data.httpclient.RetrofitClient
import okhttp3.logging.HttpLoggingInterceptor
import com.sasaj.data.mappers.CityDtoToDomainMapper
import com.sasaj.domain.NetworkManager
import com.sasaj.domain.WeatherRepository
import com.sasaj.weatherapp.BuildConfig
import com.sasaj.weatherapp.common.CityDomainToUIMapper
import com.sasaj.weatherapp.common.NetworkManagerImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideCityDomainToUIMapper(): CityDomainToUIMapper{
        return  CityDomainToUIMapper()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return  OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return  Retrofit.Builder().baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun provideOpenWeatherMapService(retrofit: Retrofit) : OWMService {
        return  retrofit.create(OWMService::class.java)
    }

    @Provides
    @Singleton
    fun provideHttpClient(owmService: OWMService): RetrofitClient {
        return  RetrofitClient(owmService, BuildConfig.API_KEY)
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