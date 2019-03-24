package com.sasaj.weatherapp.citydetails

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.sasaj.domain.WeatherRepository
import com.sasaj.domain.common.TestTransformer
import com.sasaj.domain.entities.Weather
import com.sasaj.domain.usecases.GetWeatherForCityUseCase
import io.reactivex.Observable
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.mock

@Suppress("UNCHECKED_CAST")
class DetailsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var weatherRepository: WeatherRepository
    private lateinit var getWeatherForCityUseCase: GetWeatherForCityUseCase
    private lateinit var detailsViewModel: DetailsViewModel

    private lateinit var viewStateObserver: Observer<DetailsViewState>
    private lateinit var errorObserver: Observer<Throwable?>

    private val weatherDummy = Weather("Clear","clear sky", "", "", "","" )

    @Before
    fun setUp() {
        weatherRepository = mock(WeatherRepository::class.java)
        getWeatherForCityUseCase = GetWeatherForCityUseCase(TestTransformer(), weatherRepository)
        detailsViewModel = DetailsViewModel(getWeatherForCityUseCase)

        viewStateObserver = mock(Observer::class.java) as Observer<DetailsViewState>
        errorObserver = mock(Observer::class.java) as Observer<Throwable?>

        detailsViewModel.detailsLiveData.observeForever(viewStateObserver)
        detailsViewModel.errorState.observeForever(errorObserver)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getWeather() {

        val argument = ArgumentCaptor.forClass(DetailsViewState::class.java)
        Mockito.`when`(weatherRepository.getWeather(111)).thenReturn(Observable.just(weatherDummy))
        detailsViewModel.getWeather(111)


        Mockito.verify(viewStateObserver, Mockito.times(3)).onChanged(argument.capture())

        Assert.assertEquals(null, argument.allValues[0].weather)
        Assert.assertEquals(null, argument.allValues[1].weather)
        Assert.assertEquals(weatherDummy, argument.allValues[2].weather)
    }

}