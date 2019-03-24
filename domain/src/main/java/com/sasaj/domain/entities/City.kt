package com.sasaj.domain.entities

/**
 * Represents geographic location.
 * @param coord lat and lon ifo of the location
 * @param country ISO 3166-1 alpha-2 code of the country
 * @param id openWeatherMap id of the location
 * @param name name of the location
 */
data class City(
        val coord: Coord,
        val country: String,
        val id: Int,
        val name: String
)