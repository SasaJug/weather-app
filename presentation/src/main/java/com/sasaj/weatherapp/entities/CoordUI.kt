package com.sasaj.weatherapp.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CoordUI(
        val lat: Double,
        val lon: Double
) : Parcelable