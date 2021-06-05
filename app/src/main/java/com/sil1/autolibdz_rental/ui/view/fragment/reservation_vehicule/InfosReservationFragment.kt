package com.sil1.autolibdz_rental.ui.view.fragment.reservation_vehicule

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.ui.viewmodel.Reservation
import com.sil1.autolibdz_rental.ui.viewmodel.Vehicule
import kotlinx.android.synthetic.main.fragment_details_vehicule.*
import kotlinx.android.synthetic.main.fragment_infos_reservation.*

class InfosReservationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_infos_reservation, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val vm = ViewModelProvider(requireActivity()).get(Vehicule::class.java)
        val resViewModel = ViewModelProvider(requireActivity()).get(Reservation::class.java)

        nomVehiculeTextViewI.text = vm.marque +" "+ vm.modele
        borneDepartTextViewI.text = "Borne" + " " +  resViewModel.nomBorneDepart
        borneDestinationTextViewI.text = "Borne" + " " + resViewModel.nomBorneDestination
        priceTextViewI.text = ((resViewModel.distanceEstime * 162.34) + (resViewModel.tempsEstimeEnSecondes * 48.7 / 60)).toString() + "DA"
        }
}
