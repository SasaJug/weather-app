package com.sasaj.weatherapp.citydetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sasaj.weatherapp.R
import com.sasaj.weatherapp.WeatherApplication
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
     * The dummy content this fragment is presenting.
     */
    private var cityId: Int?= null

    private lateinit var vmWeatherDetails: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as WeatherApplication).createWeatherDetailsComponent().inject(this)

        vmWeatherDetails = ViewModelProviders.of(this, detailsVMFactory).get(DetailsViewModel::class.java)

        arguments?.let {
            if (it.containsKey(ARG_CITY_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                cityId = it.getInt(ARG_CITY_ID)
//                activity?.toolbar_layout?.title = item?.content
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
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Show the dummy content as text in a TextView.
//        item?.let {
//            rootView.location_detail.text = it.details
//        }

        return inflater.inflate(R.layout.location_detail, container, false)
    }

    override fun onResume() {
        super.onResume()
        cityId?.let { vmWeatherDetails.getWeather(cityId!!) }

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
            weather_detail.text = detailsViewState.weather.toString()
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

    private fun renderError(throwable: Throwable?){
        (activity as LocationDetailActivity).showDialogMessage(throwable?.message, throwable.toString())
    }

    companion object {
        /**
         * The fragment argument representing the city ID that this fragment
         * represents.
         */
        val TAG = LocationDetailFragment::class.java.simpleName
        const val ARG_CITY_ID = "city_id"
    }
}
