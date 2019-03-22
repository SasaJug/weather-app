package com.sasaj.data.entities

import com.google.gson.annotations.SerializedName

data class CityDto(
        @SerializedName("coord")val coordDto: CoordDto,
        val country: String,
        val id: Int,
        val name: String
)