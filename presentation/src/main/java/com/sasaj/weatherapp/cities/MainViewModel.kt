package com.sasaj.weatherapp.cities

import android.arch.lifecycle.MutableLiveData
import android.content.ContentValues.TAG
import android.util.Log
import com.sasaj.domain.entities.City
import com.sasaj.domain.usecases.GetCitiesUseCase
import com.sasaj.weatherapp.common.BaseViewModel
import com.sasaj.weatherapp.common.CityDomainToUIMapper
import com.sasaj.weatherapp.common.SingleLiveEvent


class MainViewModel(private val getCitiesUseCase: GetCitiesUseCase, private val cityDomainToUIMapper: CityDomainToUIMapper) : BaseViewModel() {

    val mainLiveData: MutableLiveData<MainViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        mainLiveData.value = MainViewState()
    }

    fun getCities() {
        val mainViewState = mainLiveData.value?.copy(state = MainViewState.LOADING)
        mainLiveData.value = mainViewState

        addDisposable(getCitiesUseCase.getCities()
                .subscribe(
                        { list: List<City> ->
                            val mappedList = list.map { city -> cityDomainToUIMapper.mapFrom(city) }
                            val newMainViewState = mainLiveData.value?.copy(state = MainViewState.CITIES_LOADED, citiesList = mappedList)
                            mainLiveData.value = newMainViewState
                            errorState.value = null
                        },
                        { e ->
                            errorState.value = e
                        },
                        { Log.i(TAG, "Cities list fetched") }
                )
        )
    }

}