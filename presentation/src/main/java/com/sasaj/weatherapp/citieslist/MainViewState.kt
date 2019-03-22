package com.sasaj.weatherapp.citieslist

import com.sasaj.domain.entities.City

data class MainViewState(var state: Int = -1, var citiesList: List<City>? = null) {
    companion object {
        const val LOADING: Int = 0
        const val CITIES_LOADED: Int = 1
    }
}