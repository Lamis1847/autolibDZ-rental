package com.sil1.autolibdz_rental.ui.view.fragment.map_display

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.maps.GeoApiContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.maps.DistanceMatrixApi
import com.google.maps.PendingResult
import com.google.maps.model.DistanceMatrix
import com.google.maps.model.TravelMode
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.data.model.Borne
import com.sil1.autolibdz_rental.ui.view.activity.MyDrawerController
import com.sil1.autolibdz_rental.ui.viewmodel.Reservation
import com.sil1.autolibdz_rental.ui.viewmodel.Vehicule
import kotlinx.android.synthetic.main.map_display_fragment.*
import java.io.IOException
import java.text.DecimalFormat
import java.util.*
import kotlin.properties.Delegates


class MapDisplayFragment : Fragment() , OnMapReadyCallback , GoogleMap.OnMarkerClickListener {
    private lateinit var mMap : GoogleMap
    private var mapReady = false
    private lateinit var marker: Marker
    private lateinit var viewModel: MapDisplayViewModel
    private val TAG = "MapsFragment"
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    private var clickedOnBorneDepart : Boolean = false
    private var clickedOnBorneDestination : Boolean = false
    private var totalDistance = 0L
    private lateinit var origin : LatLng
    private lateinit var destination : LatLng
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private  var timeHumanReadable = ""
    private  var timeInSeconds  = 0.0
    private  var idBorneDepart : Int = 0
    private  var idBorneDestination : Int = 0
    private var myDrawerController: MyDrawerController? = null

    companion object {
        fun newInstance() = MapDisplayFragment()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        myDrawerController = try {
            activity as MyDrawerController
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement MyDrawerController")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.map_display_fragment, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment

        mapFragment.getMapAsync(this)
        myDrawerController?.setDrawer_UnLocked();

        return view

    }
    override fun onDestroyView() {
        super.onDestroyView()
        myDrawerController?.setDrawer_Locked()
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
        //this vm is used to pass data between this fragment and list vehicule fragment
        val resViewModel = ViewModelProvider(requireActivity()).get(Reservation::class.java)
        viewModel = ViewModelProvider(this).get(MapDisplayViewModel::class.java)
        //bitmap = BitmapFactory.decodeResource(resources,R.drawable.ic_borne_marker)
        viewModel.bornes.observe(this, Observer {
                bornes ->
                updateMap(bornes)
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
            Toast.makeText(requireActivity(),"Yofffffff",Toast.LENGTH_SHORT).show()

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
        buttonSuivant.setOnClickListener(){
            makeDistanceCalculationCall(origin,destination)
            Log.i(TAG,"it stopped here")

            resViewModel.idBorneDepart = idBorneDepart
            resViewModel.idBorneDestination = idBorneDestination
            resViewModel.tempsEstimeEnSecondes = timeInSeconds
            resViewModel.tempsEstimeHumanReadable = timeHumanReadable
            resViewModel.distanceEstime = totalDistance
            Log.i(TAG,"it works here")
            findNavController().navigate(R.id.action_nav_home_to_listeVehiculeFragment)
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
        val id = marker.tag as? Int

        // Check if a click count was set, then display the click count.
        id?.let {
            Log.i(
                TAG,
                "${marker.title} has been clicked on",
            )
            if(clickedOnBorneDepart) {
                buttonChoixBorneDepart.text = "Borne de " + marker.title
                origin = marker.position
                idBorneDepart = id

            }
            else {
                if(clickedOnBorneDestination) {
                    buttonChoixBorneDestination.text = "Borne de " + marker.title
                    destination = marker.position
                    idBorneDestination = id

                }
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

                        marker.tag = borne.idBorne
                    }
                }
                //test :
                /*  origin = LatLng(36.7029047,3.1428341)
                destination = LatLng(36.78136937881909, 3.057437731395777)
                makeDistanceCalculationCall(origin,destination)*/
                // Set a listener for marker click.

                mMap.setOnMarkerClickListener(this)
            }
        }
        catch (e: IOException) {
            Log.i("error:",e.toString())
        }

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    /*
    * Cette fonction nous permet d'instancier distancematrixAPI afin de calculer
    * le temps estimée et la durée estimé du trajet entre deux bornes
    */

    fun geoContextDistanceApi(): GeoApiContext {
        return GeoApiContext.Builder()
            .apiKey(getString(R.string.api_key))
            .build()
    }

    /*
   * Cette fonction nous permet de faire une transformation du metre vers le km
   */

    fun getDistanceInKm(totalDistance: Double): String {
        if (totalDistance == 0.0 || totalDistance < -1)
            return "0 Km"
        else if (totalDistance > 0 && totalDistance < 1000)
            return totalDistance.toInt().toString() + " meters"
        val df = DecimalFormat("#.##")
        return df.format(totalDistance / 1000) + " Km"
    }
    private fun getDistance(): String {
        Log.e(TAG, "Total Distance -> $totalDistance")
        return getDistanceInKm(totalDistance.toDouble())
    }

    /*
   * Cette fonction nous permet de calculer
   * le temps estimée et la durée estimé du trajet entre deux bornes
   */
    private fun makeDistanceCalculationCall(depart:LatLng , dest:LatLng ) {
        Toast.makeText(requireActivity(),"Yo je suis la ${depart.longitude}",Toast.LENGTH_SHORT).show()

        val origin = arrayOf(depart.latitude.toString() + "," + depart.longitude)
        val destination = arrayOf(dest.latitude.toString() + "," + dest.longitude.toString())
        DistanceMatrixApi.getDistanceMatrix(geoContextDistanceApi(), origin, destination)
            .mode(TravelMode.DRIVING)
            .setCallback(object : PendingResult.Callback<DistanceMatrix> {
                override fun onResult(result: DistanceMatrix) {
                    val timeHumanReadable = result.rows[0].elements[0].duration.humanReadable
                    val timeInSeconds = result.rows[0].elements[0].duration.inSeconds
                    totalDistance = result.rows[0].elements[0].distance.inMeters
                    Log.e(TAG, "Total Duration in seconds -> $timeInSeconds")
                    Log.e(TAG, "Total Duration -> $timeHumanReadable")
                    Log.e(TAG, "Total Distance -> ${getDistance()}")
                }

                override fun onFailure(e: Throwable) {
                    Log.i("error",e.message.toString())
                    e.printStackTrace()
                }
            })
    }









}



