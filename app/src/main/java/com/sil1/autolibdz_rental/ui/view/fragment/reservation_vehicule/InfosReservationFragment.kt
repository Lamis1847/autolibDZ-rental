package com.sil1.autolibdz_rental.ui.view.fragment.reservation_vehicule

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.data.model.ReservationModel
import com.sil1.autolibdz_rental.data.repositories.ReservationRepository
import com.sil1.autolibdz_rental.ui.viewmodel.Reservation
import com.sil1.autolibdz_rental.ui.viewmodel.Vehicule
import kotlinx.android.synthetic.main.fragment_infos_reservation.*
import com.sil1.autolibdz_rental.data.model.VehiculeModel
import com.sil1.autolibdz_rental.ui.view.fragment.ListeVehiculeViewModel


class InfosReservationFragment : Fragment() {
    private lateinit var viewModel: InfosReservationViewModel
    lateinit var resViewModel : Reservation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(InfosReservationViewModel::class.java)

        return inflater.inflate(R.layout.fragment_infos_reservation, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Toast.makeText(requireActivity(), "Disabled Back Press", Toast.LENGTH_SHORT).show()
            }
        })
        val vm = ViewModelProvider(requireActivity()).get(Vehicule::class.java)
        val resViewModel = ViewModelProvider(requireActivity()).get(Reservation::class.java)

        nomVehiculeTextViewI.text = vm.marque +" "+ vm.modele
        borneDepartTextViewI.text = "Borne" + " " +  resViewModel.nomBorneDepart
        borneDestinationTextViewI.text = "Borne" + " " + resViewModel.nomBorneDestination
        var prix = ((resViewModel.distanceEstime * 0.621371 * 162.34) + (resViewModel.tempsEstimeEnSecondes * 48.7 / 60))
        priceTextViewI.text = prix.toString() + "DA"
        
        Log.i("distanceEstime",resViewModel.distanceEstime.toString() )
        Log.i("tempsEstimeEnSecondes",resViewModel.tempsEstimeEnSecondes.toString() )


        confirmerButton.setOnClickListener {
            var reservation = ReservationModel(
                "En cours",
                vm.numChassis,
                3,
                resViewModel.idBorneDepart,
                resViewModel.idBorneDestination,
                resViewModel.tempsEstimeEnSecondes.toInt(),
                resViewModel.distanceEstime.toFloat(),
                prix.toFloat()
            )
            viewModel.ajouterReservation(reservation)

            viewModel.reservation.observe(viewLifecycleOwner, Observer {
                val data = viewModel.reservation.value
                val bundle = bundleOf("codePin" to (data?.codePin), "id" to (data?.id))
              findNavController().navigate(R.id.action_infosReservationFragment_to_vehiculeReserveFragment,bundle)

            })
            }
        annulerButton.setOnClickListener {
            findNavController().navigate(R.id.action_infosReservationFragment_to_nav_home)
        }
        }



}
