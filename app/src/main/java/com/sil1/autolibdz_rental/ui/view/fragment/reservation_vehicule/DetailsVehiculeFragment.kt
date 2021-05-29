package com.sil1.autolibdz_rental.ui.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.ui.viewmodel.Vehicule
import kotlinx.android.synthetic.main.fragment_details_vehicule.*


class DetailsVehiculeFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details_vehicule, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val vm = ViewModelProvider(requireActivity()).get(Vehicule::class.java)
            nomVehiculeD.text = vm.marque +" "+ vm.modele
            matriculeVehiculeD.text = vm.modele
            marqueVehiculeD.text = vm.marque
            etatVehiculeD.text = vm.etat

            goBackButtonDetailsVehicule.setOnClickListener{ this.findNavController().navigate(R.id.action_DetailsVehiculeFragment_pop)

        }
    }
}

