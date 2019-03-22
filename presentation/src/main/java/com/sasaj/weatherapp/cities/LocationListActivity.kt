package com.sasaj.weatherapp.cities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.sasaj.domain.entities.City
import com.sasaj.weatherapp.R
import com.sasaj.weatherapp.WeatherApplication
import com.sasaj.weatherapp.common.BaseActivity
import kotlinx.android.synthetic.main.activity_location_list.*
import kotlinx.android.synthetic.main.location_list.*
import javax.inject.Inject


/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [LocationDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class LocationListActivity : BaseActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    @Inject
    lateinit var mainVMFactory: MainVMFactory

    private lateinit var vm: MainViewModel
    private lateinit var adapter: SimpleItemRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_list)

        (application as WeatherApplication).createCitiesListComponent().inject(this)

        vm = ViewModelProviders.of(this, mainVMFactory).get(MainViewModel::class.java)

        vm.mainLiveData.observe(this, Observer { mainState -> handleResponse(mainState) })
        vm.errorState.observe(this, Observer { throwable ->
            throwable?.let {
                renderErrorState(it)
            }
        })

        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        if (location_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        setupRecyclerView(location_list)

    }

    override fun onResume() {
        super.onResume()
        vm.getCities()
    }

    override fun onDestroy() {
        super.onDestroy()
        (application as WeatherApplication).releaseCitiesListComponent()
    }


    private fun handleResponse(mainViewState: MainViewState?) {
        when (mainViewState?.state) {
            MainViewState.LOADING -> renderLoadingState()
            MainViewState.CITIES_LOADED -> renderShowList(mainViewState.citiesList)
        }
    }


    private fun renderShowList(list: List<City>?) {
        adapter.setCities(list!!)
        hideProgress()
    }

    private fun renderLoadingState() {
        showProgress()
    }


    private fun renderErrorState(throwable: Throwable?) {
        hideProgress()
        Log.e(TAG, "Error ", throwable)
        showDialogMessage("Error ", throwable.toString())
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SimpleItemRecyclerViewAdapter(this, twoPane)
        recyclerView.adapter = adapter
    }


    companion object {
        private val TAG = LocationListActivity::class.java.simpleName
    }
}
