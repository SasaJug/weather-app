package com.sasaj.weatherapp.citydetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sasaj.weatherapp.R
import com.sasaj.weatherapp.WeatherApplication
import com.sasaj.weatherapp.entities.CityUI
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.location_detail.*
import javax.inject.Inject

/**
 * A fragment representing a single Location detail screen.
 * This fragment is either contained in a [LocationListActivity]
 * in two-pane mode (on tablets) or a [LocationDetailActivity]
 * on handsets.
 */
class LocationDetailFragment : Fragment() {


    @Inject
    lateinit var detailsVMFactory: DetailsVMFactory

    /**
     * The city this fragment is presenting.
     */
    private var city: CityUI? = null

    private var screenDensity: Float? = 0.0f

    private lateinit var vmWeatherDetails: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as WeatherApplication).createWeatherDetailsComponent().inject(this)

        vmWeatherDetails = ViewModelProviders.of(this, detailsVMFactory).get(DetailsViewModel::class.java)

        arguments?.let {
            if (it.containsKey(ARG_CITY)) {
                city = it.getParcelable(ARG_CITY)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vmWeatherDetails.detailsLiveData.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        vmWeatherDetails.errorState.observe(this, Observer { throwable ->
            throwable?.let { handleError(throwable) }
        })
        screenDensity = activity?.resources?.displayMetrics?.density
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.location_detail, container, false)
    }

    override fun onResume() {
        super.onResume()
        city?.let { vmWeatherDetails.getWeather(city!!.id) }

    }

    override fun onDestroy() {
        (activity?.application as WeatherApplication).releaseWeatherDetailsComponent()
        super.onDestroy()
    }

    private fun handleViewState(detailsViewState: DetailsViewState) {
        if (detailsViewState.state == DetailsViewState.LOADING)
            showProgress(true)
        else if (detailsViewState.state == DetailsViewState.WEATHER_LOADED) {
            showProgress(false)
            val weather = detailsViewState.weather
            val iconSize = 48 * screenDensity!!
            weather?.let {
                Picasso.get().load(weather.iconUri).resize(iconSize.toInt(), iconSize.toInt()).centerCrop().into(weatherIcon)
                title.text = weather.main
                description.text = weather.description
                temperature.text = "${weather.temperature} °C"
                pressure.text = "${weather.pressure} hPa"
                wind.text = "${weather.windSpeed} m/s"
                humidity.text = "${weather.humidity} %"
            }
        }
    }

    private fun handleError(throwable: Throwable?) {
        showProgress(false)
        renderError(throwable)
    }

    private fun showProgress(show: Boolean) {
        if (show)
            loadingProgress.visibility = View.VISIBLE
        else
            loadingProgress.visibility = View.GONE
    }

    private fun renderError(throwable: Throwable?) {
        (activity as LocationDetailActivity).showDialogMessage(throwable?.message, throwable.toString())
    }

    companion object {
        /**
         * The fragment argument representing the city ID that this fragment
         * represents.
         */
        val TAG = LocationDetailFragment::class.java.simpleName
        const val ARG_CITY = "city"
    }
}
