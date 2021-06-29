package com.sil1.autolibdz_rental.ui.view.fragment.reservation_vehicule

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.data.model.ReservationModel
import com.sil1.autolibdz_rental.data.repositories.ReservationRepository
import com.sil1.autolibdz_rental.ui.viewmodel.Reservation
import com.sil1.autolibdz_rental.ui.viewmodel.Vehicule
import kotlinx.android.synthetic.main.fragment_infos_reservation.*
import com.sil1.autolibdz_rental.data.model.ReservationResponse
import com.sil1.autolibdz_rental.data.model.VehiculeModel
import com.sil1.autolibdz_rental.data.room.RoomService
import com.sil1.autolibdz_rental.ui.view.activity.MyDrawerController
import com.sil1.autolibdz_rental.ui.view.fragment.ListeVehiculeViewModel
import com.sil1.autolibdz_rental.ui.view.fragment.map_display.MapDisplayViewModel
import com.sil1.autolibdz_rental.ui.view.fragment.map_display.MapDisplayViewModelFactory
import com.sil1.autolibdz_rental.utils.sharedPrefFile


class InfosReservationFragment : Fragment() {
    private lateinit var viewModel: InfosReservationViewModel
    private var myDrawerController: MyDrawerController? = null
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
        myDrawerController?.setDrawer_Locked();

        return inflater.inflate(R.layout.fragment_infos_reservation, container, false)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        //myDrawerController?.setDrawer_UnLocked()
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val sharedPref = RoomService.context.getSharedPreferences(
            sharedPrefFile, Context.MODE_PRIVATE
        )

        val token = sharedPref.getString("token","defaultvalue").toString()
        val userID = sharedPref.getString("userID","defaultvalue")?.toInt()
        val factory = InfosReservationViewModelFactory(token)
        viewModel = ViewModelProviders.of(this, factory).get(InfosReservationViewModel::class.java)

        val vm = ViewModelProvider(requireActivity()).get(Vehicule::class.java)
        val vmRes = ViewModelProvider(requireActivity()).get(Reservation::class.java)
        Log.i("InfosRes",vm.latitute.toString())
        nomVehiculeTextViewI.text = vm.marque +" "+ vm.modele
        borneDepartTextViewI.text = "Borne" + " " +  vmRes.nomBorneDepart
        borneDestinationTextViewI.text = "Borne" + " " + vmRes.nomBorneDestination
        val distanceEstime = vmRes.distanceEstime
        val tempsEstime = vmRes.tempsEstimeEnSecondes
        Log.i("InfosRes","distanceEstime"+distanceEstime.toString() )
        Log.i("InfosRes","tempsEstimeEnSecondes"+tempsEstime.toString() )
        //j'ai changé la formule car c trop cher
       // var prix = ((distanceEstime * 162.34 / 1000) + (tempsEstime * 48.7 / 60))
        var prix = ((distanceEstime * 100 / 1000) + (tempsEstime * 30/ 60))

        priceTextViewI.text = prix.toString() + "DA"


        confirmerButton.setOnClickListener {
            var reservation = userID?.let { it1 ->
                ReservationModel(
                    "En cours",
                    it1,
                    vm.numChassis,
                    vmRes.idBorneDepart,
                    vmRes.idBorneDestination,
                    vmRes.tempsEstimeEnSecondes.toInt(),
                    vmRes.distanceEstime.toFloat(),
                    prix.toFloat()
                )
            }
            if (reservation != null) {
                viewModel.ajouterReservation(reservation,token)
            }

            viewModel.reservation.observe(viewLifecycleOwner, Observer {
                val data = viewModel.reservation.value
                val bundle = bundleOf("codePin" to (data?.codePin))
              findNavController().navigate(R.id.action_infosReservationFragment_to_vehiculeReserveFragment,bundle)

            })
            }
        annulerButton.setOnClickListener {
            findNavController().navigate(R.id.action_infosReservationFragment_to_nav_home)
        }
        }



}
