package com.sasaj.weatherapp.cities

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sasaj.domain.entities.City
import com.sasaj.weatherapp.citydetails.LocationDetailActivity
import com.sasaj.weatherapp.citydetails.LocationDetailFragment
import com.sasaj.weatherapp.R
import com.sasaj.weatherapp.dummy.DummyContent
import kotlinx.android.synthetic.main.location_list_content.view.*


class SimpleItemRecyclerViewAdapter(private val parentActivity: LocationListActivity,
                                    private val twoPane: Boolean) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener
    private var cities = listOf<City>()

    init {
        onClickListener = View.OnClickListener { v ->
            val city = v.tag as City
            if (twoPane) {
                val fragment = LocationDetailFragment().apply {
                    arguments = Bundle().apply {
                        putInt(LocationDetailFragment.ARG_CITY_ID, city.id)
                    }
                }
                parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.location_detail_container, fragment)
                        .commit()
            } else {
                val intent = Intent(v.context, LocationDetailActivity::class.java).apply {
                    putExtra(LocationDetailFragment.ARG_CITY_ID, city.id)
                }
                v.context.startActivity(intent)
            }
        }
    }

    fun setCities(cities: List<City>) {
        this.cities = cities
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.location_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = cities[position]
        holder.idView.text = city.name
        holder.contentView.text = city.country

        with(holder.itemView) {
            tag = city
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = cities.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.id_text
        val contentView: TextView = view.content
    }
}