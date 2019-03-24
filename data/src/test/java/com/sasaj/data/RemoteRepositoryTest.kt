package com.sasaj.data

import com.sasaj.data.common.LocationDtoToDomainMapper
import com.sasaj.data.entities.*
import com.sasaj.data.httpclient.OWMService
import com.sasaj.data.httpclient.RetrofitClient
import com.sasaj.domain.entities.Coord
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.junit.Rule
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit


@RunWith(JUnit4::class)
open class RemoteRepositoryTest {

    private val mainField = "Clear"
    private val description = "Clear sky"
    private val temperature = 15.5
    private val pressure = 1024.44
    private val humidity = 45.5
    private val windspeed = 12.4

    @get:Rule
    var rule = MockitoJUnit.rule()

    lateinit var remoteRepository: RemoteRepository

    @Mock
    lateinit var service :OWMService

    lateinit var retrofitClient: RetrofitClient

    lateinit var locationDtoToDomainMapper: LocationDtoToDomainMapper

    @Before
    fun setUp() {
        `when`(service.getWeatherForCityId(111,"metric", "en", "111")).thenReturn(Single.just(getMockLocation()))
        retrofitClient = RetrofitClient(service, "111")
        locationDtoToDomainMapper = LocationDtoToDomainMapper()
        remoteRepository = RemoteRepository(retrofitClient, locationDtoToDomainMapper)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getWeather() {
        val testObserver = TestObserver<com.sasaj.domain.entities.Weather>()
        remoteRepository.getWeather(111).subscribe(testObserver)

        testObserver.assertValue{w -> w.main == "Clear"}
        testObserver.assertNoErrors()
        testObserver.assertComplete()
    }

    /*
    * Generates mock LocationDto object. Only fields of interest (which are used in com.sasaj.domain.entities.WeatherDto instance) are  filled,
    * other fields are set to dummy values.
    *
    * */
    private fun getMockLocation() : LocationDto{
        val main = MainDto(humidity, pressure, temperature, 0.0,0.0)
        val clouds = CloudsDto(0)
        val coord = CoordDto(0.0,0.0)
        val sys = SysDto("AT", 0, 0.0, 0, 0, 0)
        val weatherList : List<WeatherDto> = arrayListOf(WeatherDto(description,"icon", 111, mainField))
        val wind = WindDto(12, windspeed)
        return LocationDto("", clouds,0, coord, 0, 0, main, "City", sys, 0, weatherList, wind)
    }
}