package com.sasaj.weatherapp.citydetails

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.sasaj.domain.usecases.GetWeatherForCityUseCase

class DetailsVMFactory(private val getWeatherForCityUseCase: GetWeatherForCityUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(getWeatherForCityUseCase) as T
    }
}