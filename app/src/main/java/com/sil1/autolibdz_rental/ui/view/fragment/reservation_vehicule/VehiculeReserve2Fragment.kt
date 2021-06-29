package com.sil1.autolibdz_rental.ui.view.fragment.reservation_vehicule

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.kaopiz.kprogresshud.KProgressHUD
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.ui.view.activity.MyDrawerController
import com.sil1.autolibdz_rental.ui.view.fragment.stripe_card.StripeCardViewModel
import com.sil1.autolibdz_rental.ui.viewmodel.Reservation
import com.sil1.autolibdz_rental.ui.viewmodel.Vehicule
import kotlinx.android.synthetic.main.fragment_details_vehicule.*
import kotlinx.android.synthetic.main.fragment_vehicule_reserve2.*
import kotlinx.android.synthetic.main.stripe_card_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

class VehiculeReserve2Fragment : Fragment() {
    private val TAG = "_VehiculeDeverouillerFragment"
    private lateinit var viewModel: InfosReservationViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    var requestingLocationUpdates = true
    private lateinit var mCurrentLocation : Location
    private lateinit var locationRequest : LocationRequest
    private lateinit var vehiculeLocation : LatLng
    private var myDrawerController: MyDrawerController? = null
    private var idReservation = 0
    private lateinit var hud: KProgressHUD
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
        var codePin= arguments?.get("codePin")
        code = codePin.toString()

        var idReservation= arguments?.get("id")


        myDrawerController?.setDrawer_Locked();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        getCurrentLocation()
        createLocationRequest()
        vehiculeLocation = LatLng(36.694709,4.058017) //36.694732,4.058094
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(requireActivity())
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        calculateDistance(36.702799,4.059917,36.694697,4.058107)

        locationCallback = object : LocationCallback() {

            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    calculateDistance(vehiculeLocation.latitude,vehiculeLocation.longitude,location.latitude,location.longitude)
                    // write the condition to enable deverouiller button
                    Log.i(TAG,"resss == ${location.latitude}")
                }
            }
        }
        return inflater.inflate(R.layout.fragment_vehicule_reserve2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val vm = ViewModelProvider(requireActivity()).get(Vehicule::class.java)
        viewModel = ViewModelProvider(requireActivity()).get(InfosReservationViewModel::class.java)

        signalerFinTrajetBtn.setOnClickListener { signalFinTrajet() }

        Glide.with(requireActivity()).load(vm.secureUrl).into(imageVehicule1)
        codePINTextView1.text = code

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

    fun signalFinTrajet() {
        //create payment intent
        val vmRes = ViewModelProvider(requireActivity()).get(Reservation::class.java)
        viewModel.getTrajet(requireContext(), idReservation)
        hud = KProgressHUD.create(requireActivity())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Patientez s'il vous plait")
        hud.show()
        viewModel.trajet.observe(requireActivity(), Observer {
            if (viewModel.trajet.value?.dateFin != null) {
                val handler = Handler()
                handler.postDelayed(Runnable { hud.dismiss() }, 500)
                Log.i("tralala", "date fin atteinte")
                val bundle = bundleOf(
                    "idReservation" to idReservation,
                    "borneDepart" to vmRes.nomBorneDepart,
                    "borneArrivee" to vmRes.nomBorneDestination,
                    "kilometres" to viewModel.trajet.value!!.kmParcourue,
                    "temps" to viewModel.trajet.value!!.tempsEstime,
                    "prixAPayer" to viewModel.trajet.value!!.prixAPayer
                )
                requireActivity().findNavController(R.id.payment_test).navigate(
                    R.id.action_vehiculeReserve2Fragment_to_infosTrajetFragment,
                    bundle
                )
            }
            else if ((viewModel.trajet.value?.dateFin == null) && (viewModel.trajet.value != null)){
                val handler = Handler()
                handler.postDelayed(Runnable { hud.dismiss() }, 500)
                val dialog = MaterialDialog(requireActivity())
                    .title(R.string.signalODB)
                    .message(R.string.signalODBDetail)
                    .positiveButton(R.string.yes) { dialog -> dialog.dismiss()
                    }

                dialog.show()
                Log.i("tralala", "date fin NOOON atteinte")
            }
        })
    }

}