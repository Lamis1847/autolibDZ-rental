package com.sil1.autolibdz_rental.ui.view.fragment.reservation_vehicule

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.maps.DistanceMatrixApi
import com.google.maps.GeoApiContext
import com.google.maps.PendingResult
import com.google.maps.model.DistanceMatrix
import com.google.maps.model.TravelMode
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.ui.view.activity.MyDrawerController
import com.sil1.autolibdz_rental.ui.viewmodel.Reservation
import com.sil1.autolibdz_rental.ui.viewmodel.Vehicule
import kotlinx.android.synthetic.main.fragment_details_vehicule.*
import kotlinx.android.synthetic.main.fragment_vehicule_reserve2.*

class VehiculeReserve2Fragment : Fragment() {
    private val TAG = "_VehiculeDeverouillerFragment"
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    var requestingLocationUpdates = true
    var totalDistance : Long = 0
    private lateinit var mCurrentLocation : Location
    private lateinit var locationRequest : LocationRequest
    private lateinit var vehiculeLocation : LatLng
    private lateinit var vm : Vehicule
    private var myDrawerController: MyDrawerController? = null
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        myDrawerController = try {
            activity as MyDrawerController
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement MyDrawerController")
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
       // myDrawerController?.setDrawer_UnLocked()
    }

    var code=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         vm = ViewModelProvider(requireActivity()).get(Vehicule::class.java)

        var codePin= arguments?.get("codePin")
        code = codePin.toString()
        myDrawerController?.setDrawer_Locked();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        getCurrentLocation()
        createLocationRequest()
        vehiculeLocation = LatLng(vm.latitute.toDouble(), vm.longitude.toDouble()) //36.7045275 + 3.1730424
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(requireActivity())
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        locationCallback = object : LocationCallback() {

            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    Log.i(TAG,"Current location : latitude : ${location.latitude} ,longitude ${location.longitude}")
                    Log.i(TAG,"Vehicule location : latitude : ${vehiculeLocation.latitude} ,longitude ${vehiculeLocation.longitude}")

                    var res = calculateDistance(vehiculeLocation.latitude,vehiculeLocation.longitude,location.latitude,location.longitude)
                    //var currentLocation : LatLng = LatLng(location.latitude,location.longitude)
                   // makeDistanceCalculationCall(vehiculeLocation,currentLocation)
                    // write the condition to enable deverouiller button
                    if(res<20){
                        deverrouillerButton1?.isEnabled = true
                    }

                }
            }
        }
        return inflater.inflate(R.layout.fragment_vehicule_reserve2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Glide.with(requireActivity()).load(vm.secureUrl).into(imageVehicule1)
        Log.i(TAG,"vehicule location : ${vm.longitude} ${vm.latitute}")
        codePINTextView1.text = code
        verrouillerButton1?.isEnabled = false
        deverrouillerButton1?.isEnabled = false
        deverrouillerButton1.setOnClickListener {
            verrouillerButton1?.isEnabled = true
            deverrouillerButton1?.isEnabled = false
        }
        verrouillerButton1.setOnClickListener {
            deverrouillerButton1?.isEnabled = true
            verrouillerButton1?.isEnabled = false
        }
    }
    override fun onResume() {
        super.onResume()
        if (requestingLocationUpdates) startLocationUpdates()
    }
    fun createLocationRequest() {
        locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }!!
    }
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }
    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
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
                    mCurrentLocation = it
                    // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,10f))

                }
                else {

                    Log.i(TAG,"current Location : null")
                    //in case current location is null

                }
            }
        }
    }

    private fun checkPermission() {
        val perms = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION)
        val permsRequestCode = 300;
        ActivityCompat.requestPermissions(requireActivity(), perms, permsRequestCode)

    }
    fun calculateDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Float {
        val results = FloatArray(1)
        Location.distanceBetween(lat1, lng1, lat2, lng2, results)
        // distance in meter
        Log.i(TAG,"Distance : ${results[0]}")
        return results[0]
    }

    fun geoContextDistanceApi(): GeoApiContext {
        return GeoApiContext.Builder()
            .apiKey(getString(R.string.api_key))
            .build()
    }
    private fun makeDistanceCalculationCall(depart:LatLng , dest:LatLng ) {

        val origin = arrayOf(depart.latitude.toString() + "," + depart.longitude)
        val destination = arrayOf(dest.latitude.toString() + "," + dest.longitude.toString())
        DistanceMatrixApi.getDistanceMatrix(geoContextDistanceApi(), origin, destination)
            .mode(TravelMode.DRIVING)
            .setCallback(object : PendingResult.Callback<DistanceMatrix> {
                override fun onResult(result: DistanceMatrix) {
                    totalDistance = result.rows[0].elements[0].distance.inMeters

                    Log.e(TAG, "Total Distance -> $totalDistance")

                }
                override fun onFailure(e: Throwable) {
                    Log.i("error",e.message.toString())
                    e.printStackTrace()
                }
            })

    }

}