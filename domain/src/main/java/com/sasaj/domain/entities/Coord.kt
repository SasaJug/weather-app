package com.sasaj.domain.entities

/**
 * Class containing latilude and longitude of a {@link com.sasaj.domain.entities.Location}
 * @param lat lattitude
 * @param lon longitude
 */
data class Coord(
        val lat: Double,
        val lon: Double
)