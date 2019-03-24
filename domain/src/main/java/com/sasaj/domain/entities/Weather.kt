package com.sasaj.domain.entities


/**
 * Data class which collects all weather data from the response relevant in the application.
 *
 * @param main main weather characteristic
 * @param description more detailed description of weather conditions
 * @param temperature temperature in specified units, default Kelvin
 * @param windSpeed wind speed in m/s
 * @param humidity humidity in %
 * @param pressure atmospheric pressure in hPa
 * @param iconUri url of the .png file of icon for current weather conditions, <a href= "http://openweathermap.org/img/w/10d.png">example : http://openweathermap.org/img/w/10d.png </a>
 *
 */

data class Weather (

        val main : String = "N/A",
        val description : String = "N/A",
        val temperature : String = "N/A",
        val windSpeed : String = "N/A",
        val humidity : String = "N/A",
        val pressure: String = "N/A",
        val iconUri : String = "N/A"

)