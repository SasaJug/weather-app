package com.sasaj.data.entities

import  com.google.gson.annotations.SerializedName

data class LocationDto(
        val base: String,
        @SerializedName("clouds")
        val cloudsDto: CloudsDto,
        val cod: Int,
        @SerializedName("coord")
        val coord: CoordDto,
        val dt: Int,
        val id: Int,
        @SerializedName("main")
        val mainDto: MainDto,
        val name: String,
        @SerializedName("sys")
        val sysDto: SysDto,
        val visibility: Int,
        @SerializedName("weather")
        val weatherList: List<WeatherDto>,
        @SerializedName("wind")
        val windDto: WindDto
)