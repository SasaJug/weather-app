package com.sasaj.domain.entities

data class Weather (

        //Main weather characteristic in specified language, default English
        val main : String,

        //Description in specified language, default English
        val description : String,

        //Temperature in specified units, default Kelvin
        val temperature : Double,

        //Wind speed in m/s
        val windSpeed : Double,

        //Humidity in %
        val humidity : Int,

        //Atmospheric pressure in hPa
        val pressure: Int,

        //Link to the icon representing current weather and time of day
        //Example: http://openweathermap.org/img/w/10d.png
        val iconUri : String

)