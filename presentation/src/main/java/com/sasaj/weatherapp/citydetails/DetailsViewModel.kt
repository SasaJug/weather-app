package com.sasaj.weatherapp.citydetails

import android.arch.lifecycle.MutableLiveData
import com.sasaj.domain.entities.Weather
import com.sasaj.domain.usecases.GetWeatherForCityUseCase
import com.sasaj.weatherapp.common.BaseViewModel
import com.sasaj.weatherapp.common.SingleLiveEvent


class DetailsViewModel(private val getWeatherForCityUseCase: GetWeatherForCityUseCase) : BaseViewModel() {

    val detailsLiveData: MutableLiveData<DetailsViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        detailsLiveData.value = DetailsViewState()
    }

    fun getWeather(cityId: Int) {
        val detailsViewState = detailsLiveData.value?.copy(state = DetailsViewState.LOADING)
        detailsLiveData.value = detailsViewState

        addDisposable(getWeatherForCityUseCase.getWeather(cityId)
                .subscribe(
                        { weather: Weather ->
                            val newDetailsViewState = detailsLiveData.value?.copy(state = DetailsViewState.WEATHER_LOADED, weather = weather)
                            detailsLiveData.value = newDetailsViewState
                            errorState.value = null
                        },
                        { e ->
                            errorState.value = e
                        },
                        { }
                )
        )
    }
}