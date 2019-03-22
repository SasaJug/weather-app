package com.sasaj.weatherapp.citieslist

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.sasaj.domain.usecases.GetCitiesUseCase

class MainVMFactory(private val getCitiesUseCase: GetCitiesUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(getCitiesUseCase) as T
    }
}