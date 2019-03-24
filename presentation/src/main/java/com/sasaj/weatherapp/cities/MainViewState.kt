package com.sasaj.weatherapp.cities

import com.sasaj.weatherapp.entities.CityUI

data class MainViewState(var state: Int = -1, var citiesList: List<CityUI>? = null) {
    companion object {
        const val LOADING: Int = 0
        const val CITIES_LOADED: Int = 1
    }
}