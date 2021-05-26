package com.sil1.autolibdz_rental.ui.view.fragment.map_display

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.data.model.Borne
import kotlinx.android.synthetic.main.map_display_fragment.*
import java.io.IOException
import java.util.*
import kotlin.properties.Delegates


class MapDisplayFragment : Fragment() , OnMapReadyCallback , GoogleMap.OnMarkerClickListener {
    private lateinit var mMap : GoogleMap
    private var mapReady = false
    private lateinit var marker: Marker
    private lateinit var viewModel: MapDisplayViewModel
    //private lateinit var bitmap: Bitmap
    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var geocoder: Geocoder? = null
    private val TAG = "Maps Fragment"
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var locationPermissionGranted = false
    private var clickedOnBorneDepart : Boolean = false
    private var clickedOnBorneDestination : Boolean = false
   // private var choiceDone : Boolean = false
    // The geographical location where the device is currently located. That is, the last-known
   // location retrieved by the Fused Location Provider.
    private var lastKnownLocation: Location? = null

    companion object {
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        fun newInstance() = MapDisplayFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.map_display_fragment, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment

        mapFragment.getMapAsync(this)


        // geocoder = Geocoder(context)
       // fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        return view

    }

     override fun onMapReady(googleMap : GoogleMap){
         try {
             MapsInitializer.initialize(this.activity)
         } catch (e: GooglePlayServicesNotAvailableException) {
             e.printStackTrace()
         }
         mMap = googleMap
         mapReady = true
     }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
       viewModel = ViewModelProvider(this).get(MapDisplayViewModel::class.java)

        //bitmap = BitmapFactory.decodeResource(resources,R.drawable.ic_borne_marker)
        viewModel.bornes.observe(this, Observer {
                bornes ->
                updateMap(bornes)
            // Turn on the My Location layer and the related control on the map.
            //updateLocationUI()

            // Get the current location of the device and set the position of the map.
            //getDeviceLocation()
        })

        buttonChoixBorneDepart.setOnClickListener(){
            //To know which borne the user is choosing at the moment
            clickedOnBorneDepart = true
            clickedOnBorneDestination = false

            // Set the fields to specify which types of place data to
            // return after the user has made a selection.

            val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)

            // Start the autocomplete intent.
            val intent = context?.let { it1 ->
                Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(it1)
            }
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }
        buttonChoixBorneDestination.setOnClickListener(){
            clickedOnBorneDepart = false
            clickedOnBorneDestination = true

            // Set the fields to specify which types of place data to
            // return after the user has made a selection.

            val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)

            // Start the autocomplete intent.
            val intent = context?.let { it1 ->
                Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(it1)
            }
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }

    }
    //to receive a notification when a user has picked a place
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                data?.let {

                    val place = Autocomplete.getPlaceFromIntent(data)
                    val selectedLocation = place.latLng?.let { it1 -> LatLng(it1.latitude,
                        place.latLng!!.longitude ) }

                    Log.i(TAG, "Place: ${place.name}, ${place.id}")
                    //Toast.makeText(context,"Place: ${place.name}, ${place.latLng}",Toast.LENGTH_SHORT).show()
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.latLng,10f))
                }
            }
            AutocompleteActivity.RESULT_ERROR -> {
                // TODO: Handle the error.
                data?.let {
                    val status = Autocomplete.getStatusFromIntent(data)
                    status.statusMessage?.let { it1 -> Log.i(TAG, it1) }
                }
            }
            Activity.RESULT_CANCELED -> {
                // The user canceled the operation.
            }
        }
        return
    }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onMarkerClick(marker: Marker): Boolean {

        var choiceDone : Boolean by Delegates.observable(false){
                property, oldValue, newValue ->
            buttonSuivant.visibility = View.VISIBLE
        }
        // Retrieve the data from the marker.
        val clickCount = marker.tag as? Int

        // Check if a click count was set, then display the click count.
        clickCount?.let {
            val newClickCount = it + 1
            marker.tag = newClickCount
            Log.i(
                TAG,
                "${marker.title} has been clicked $newClickCount times.",
            )
           // Toast.makeText(context,"${marker.title} has been clicked $newClickCount times.",Toast.LENGTH_SHORT).show()
            if(clickedOnBorneDepart)
                buttonChoixBorneDepart.text = "Borne de " + marker.title
            else {
                if(clickedOnBorneDestination)
                    buttonChoixBorneDestination.text = "Borne de " + marker.title
            }

        }
        if(buttonChoixBorneDepart.text != "Choisir votre borne de départ" && buttonChoixBorneDestination.text != "Choisir votre borne d\'arrivée")
                choiceDone = true

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false
    }
    private fun updateMap(bornes: ArrayList<Borne>) {
        try{
            if (mapReady) {
                bornes.forEach {
                        borne ->
                    if (borne.longitude != null && borne.latitude != null) {
                        val latlng = LatLng(borne.latitude.toDouble(), borne.longitude.toDouble())
                        marker = mMap.addMarker(
                            MarkerOptions()
                                .position(latlng)
                                // .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                                .title(borne.nomBorne))
                        marker.tag = 0
                    }
                }
                // Set a listener for marker click.
                mMap.setOnMarkerClickListener(this)
            }
        }
        catch (e: IOException) {
            Log.i("error:",e.toString())
        }

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))



    }





}



