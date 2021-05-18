package com.sil1.autolibdz_rental.ui.view.fragment.map_display

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.data.model.Borne
import com.sil1.autolibdz_rental.databinding.MapDisplayFragmentBinding
import java.util.*


class MapDisplayFragment : Fragment() {
    private lateinit var mMap : GoogleMap
    private var mapReady = false
    //rivate lateinit var bornes: ArrayList<Borne>
    private lateinit var viewModel: MapDisplayViewModel
   // lateinit var binding: MapDisplayFragmentBinding


    companion object {
        fun newInstance() = MapDisplayFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.map_display_fragment, container, false)


        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync {
                googleMap -> mMap = googleMap
                mapReady = true
        }

/*
        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: ${place.name}, ${place.id}")
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })
*/
        return rootView;

    }
    @SuppressLint("FragmentLiveDataObserve")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MapDisplayViewModel::class.java)
        viewModel.bornes.observe(this, Observer {
               bornes ->
            // this.bornes = bornes
            updateMap(bornes)
        })
    }


    private fun updateMap(bornes: ArrayList<Borne>) {

            val sydney = LatLng(-33.852, 151.211)
        mMap.addMarker(
                MarkerOptions()
                    .position(sydney)
                    .title("Marker in Sydney")
            )

            // [START_EXCLUDE silent]
        if (mapReady && bornes != null) {
            bornes.forEach {
                    borne ->
                if (borne.longitude != null && borne.latitude != null) {
                    val marker = LatLng(borne.latitude.toDouble(), borne.longitude.toDouble())
                    mMap.addMarker(MarkerOptions().position(marker).title(borne.nomBorne))
                }

            }
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
            // [END_EXCLUDE]


    }



}