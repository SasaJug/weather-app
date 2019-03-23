package com.sasaj.weatherapp.entities

import android.os.Parcelable
import com.sasaj.domain.entities.Coord
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CityUI(
        val coord: CoordUI,
        val country: String,
        val id: Int,
        val name: String
) : Parcelable