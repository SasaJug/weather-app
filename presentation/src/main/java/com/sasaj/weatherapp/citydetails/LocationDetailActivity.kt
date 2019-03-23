package com.sasaj.weatherapp.citydetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import com.sasaj.weatherapp.R
import com.sasaj.weatherapp.cities.LocationListActivity
import com.sasaj.weatherapp.common.BaseActivity
import com.sasaj.weatherapp.entities.CityUI
import kotlinx.android.synthetic.main.activity_location_detail.*

/**
 * An activity representing a single Location detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [LocationListActivity].
 */
class LocationDetailActivity : BaseActivity() {

    private lateinit var city: CityUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_detail)
        setSupportActionBar(detail_toolbar)

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        city = intent.getParcelableExtra(LocationDetailFragment.ARG_CITY) as CityUI
        supportActionBar?.title = "${city.name}, ${city.country}"
        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            val fragment = LocationDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(LocationDetailFragment.ARG_CITY, city)
                }
            }

            supportFragmentManager.beginTransaction()
                    .add(R.id.location_detail_container, fragment)
                    .commit()
        }

        fab.setOnClickListener { _ ->
            val url = "https://openweathermap.org/city/${city.id}"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    // This ID represents the Home or Up button. In the case of this
                    // activity, the Up button is shown. For
                    // more details, see the Navigation pattern on Android Design:
                    //
                    // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                    navigateUpTo(Intent(this, LocationListActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}
