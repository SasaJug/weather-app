package com.sasaj.weatherapp.cities

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.sasaj.domain.usecases.GetCitiesUseCase
import com.sasaj.weatherapp.common.CityDomainToUIMapper

class MainVMFactory(private val getCitiesUseCase: GetCitiesUseCase, private val cityDomainToUIMapper: CityDomainToUIMapper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(getCitiesUseCase, cityDomainToUIMapper) as T
    }
}