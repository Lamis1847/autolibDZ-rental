package com.sil1.autolibdz_rental.ui.view.fragment.map_display

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.maps.DistanceMatrixApi
import com.google.maps.GeoApiContext
import com.google.maps.PendingResult
import com.google.maps.model.DistanceMatrix
import com.google.maps.model.TravelMode
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.data.model.Borne
import com.sil1.autolibdz_rental.ui.view.activity.MyDrawerController
import com.sil1.autolibdz_rental.ui.viewmodel.Reservation
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
    private val TAG = "_mapsFragment"
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    private var clickedOnBorneDepart : Boolean = false
    private var clickedOnBorneDestination : Boolean = false
    private var totalDistance = 0L
    private lateinit var origin : LatLng
    private lateinit var resViewModel : Reservation
    private lateinit var destination : LatLng
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private  var timeHumanReadable = ""
    private  var timeInSeconds  = 0L
    private  var idBorneDepart : Int = 0
    private  var idBorneDestination : Int = 0
    private  var nomBorneDepart : String = ""
    private  var nomBorneDestination : String = ""
    private var myDrawerController: MyDrawerController? = null
    private val REQUEST_CHECK_SETTINGS = 0x1
    private var currentLocation : LatLng = LatLng(36.6993,3.1755)
    private lateinit var  listBornes: ArrayList<Borne>
    private  var markers : ArrayList<Marker> = ArrayList<Marker>()
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
        //in order to get the current location
        createLocationRequest()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        //myDrawerController?.setDrawer_UnLocked()
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
         resViewModel = ViewModelProvider(requireActivity()).get(Reservation::class.java)
        viewModel = ViewModelProvider(this).get(MapDisplayViewModel::class.java)
        //bitmap = BitmapFactory.decodeResource(resources,R.drawable.ic_borne_marker)
        myDrawerController?.setDrawer_UnLocked()
        viewModel.bornes.observe(this, Observer {
                bornes ->
                listBornes = bornes
                updateMap(bornes)
                getCurrentLocation()

        })

        buttonChoixBorneDepart.setOnClickListener{
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
        buttonChoixBorneDestination.setOnClickListener{

            clickedOnBorneDepart = false
            clickedOnBorneDestination = true
            //filter les bornes à afficher
            verifyBornesDeDestination()

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
        buttonSuivant.setOnClickListener{
            makeDistanceCalculationCall(origin,destination)
            resViewModel.idBorneDepart = idBorneDepart
            resViewModel.idBorneDestination = idBorneDestination
            resViewModel.nomBorneDepart = nomBorneDepart
            resViewModel.nomBorneDestination = nomBorneDestination
            findNavController().navigate(R.id.action_nav_home_to_listeVehiculeFragment)

        }


    }



    private fun getCurrentLocation(){
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            checkPermission()
        } else {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                if(it!=null) {


                    Log.i(TAG,"current Location : ${it.latitude} + ${it.longitude}")
                    currentLocation = LatLng(it.latitude,it.longitude)

                   // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,10f))

                }
                else {

                    Log.i(TAG,"current Location : null")

                    //in case current location is null
                    currentLocation = LatLng(36.6993,3.1755)

                }
                //ZOOM on current location
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,10f))


            }
        }
    }


    private fun checkPermission() {
        val perms = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION)
        val permsRequestCode = 300;
        ActivityCompat.requestPermissions(requireActivity(), perms, permsRequestCode)

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
        val markerTag = marker.tag as? Borne
        val id = markerTag?.idBorne
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
                nomBorneDepart = marker.title

            }
            else {
                if(clickedOnBorneDestination) {
                    buttonChoixBorneDestination.text = "Borne de " + marker.title
                    destination = marker.position
                    idBorneDestination = id
                    nomBorneDestination = marker.title

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
                                //launching update of the map
                                val latlng = LatLng(borne.latitude.toDouble(), borne.longitude.toDouble())
                                // add the marker to the map
                                marker = mMap.addMarker(
                                    MarkerOptions()
                                        .position(latlng)
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                                        .title(borne.nomBorne))

                                marker.tag = Borne(borne.idBorne,borne.nomBorne,borne.wilaya,borne.commune,borne.latitude,borne.longitude,
                                    borne.nbVehicules, borne.nbPlaces)
                                //add the marker to the list
                                markers.add(marker)
                }
                // Set a listener for marker click.
                mMap.setOnMarkerClickListener(this)
            }
        }
        catch (e: IOException) {
            Log.i("error:",e.toString())
        }
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
        return getDistanceInKm(totalDistance.toDouble())
    }

    /*
   * Cette fonction nous permet de calculer
   * le temps estimée et la durée estimé du trajet entre deux bornes
   */
    private fun makeDistanceCalculationCall(depart:LatLng , dest:LatLng ) {

        val origin = arrayOf(depart.latitude.toString() + "," + depart.longitude)
        val destination = arrayOf(dest.latitude.toString() + "," + dest.longitude.toString())
        DistanceMatrixApi.getDistanceMatrix(geoContextDistanceApi(), origin, destination)
            .mode(TravelMode.DRIVING)
            .setCallback(object : PendingResult.Callback<DistanceMatrix> {
                override fun onResult(result: DistanceMatrix) {
                    timeHumanReadable = result.rows[0].elements[0].duration.humanReadable
                    timeInSeconds = result.rows[0].elements[0].duration.inSeconds
                    totalDistance = result.rows[0].elements[0].distance.inMeters
                    Log.e(TAG, "Total Duration in seconds -> $timeInSeconds")
                    Log.e(TAG, "Total Duration -> $timeHumanReadable")
                    Log.e(TAG, "Total Distance -> ${getDistance()}")

                    resViewModel.tempsEstimeEnSecondes = timeInSeconds.toDouble()
                    resViewModel.tempsEstimeHumanReadable = timeHumanReadable
                    resViewModel.distanceEstime = totalDistance
                }
                override fun onFailure(e: Throwable) {
                    Log.i("error",e.message.toString())
                    e.printStackTrace()
                }
            })

    }
    fun createLocationRequest() {
        val locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val builder = locationRequest?.let {
            LocationSettingsRequest.Builder()
                .addLocationRequest(it)
        }
        val client: SettingsClient = LocationServices.getSettingsClient(requireActivity())
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder?.build())
        task.addOnSuccessListener { locationSettingsResponse ->
            // All location settings are satisfied. The client can initialize
            // location requests here.
            // ...
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(requireActivity(),
                        REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }
     /*
     *  Cette fonction permet de filtrer les bornes de destination
     *  Ainsi on affichera que les bornes dans lesquelles il y a encore
     *  des places vide pour garer son vehicule
     */
    private fun verifyBornesDeDestination(){
        try{
            if (mapReady) {
                markers.forEach {
                        markerr ->
                    if(clickedOnBorneDestination){
                        val markerTag = markerr.tag as Borne
                        markerr.isVisible = markerTag.nbPlaces != 0
                    }
                }
            }
        }
        catch (e: IOException) {
            Log.i("error:",e.toString())
        }
    }

    /*
    *  Cette fonction permet de filtrer les bornes de depart
    *  Ainsi on affichera que les bornes dans lesquelles il y a encore
    *  des vehicules disponibles à reserver
    */
    private fun verifyBornesDeDepart(){
        try{
            if (mapReady) {
                markers.forEach {
                        markerr ->
                        if(clickedOnBorneDepart){
                              val markerTag = markerr.tag as Borne
                              markerr.isVisible = markerTag.nbVehicules>0
                    }
                }
            }
        }
        catch (e: IOException) {
            Log.i("error:",e.toString())
        }
    }
}



