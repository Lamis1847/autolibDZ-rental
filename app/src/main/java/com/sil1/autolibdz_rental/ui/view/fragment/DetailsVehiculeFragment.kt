package com.sil1.autolibdz_rental.ui.view.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.ui.view.fragment.DetailsVehiculeViewModel
import com.sil1.autolibdz_rental.ui.viewmodel.Vehicule
import kotlinx.android.synthetic.main.fragment_details_vehicule.*


class DetailsVehiculeFragment : Fragment() {

    companion object {
        fun newInstance() = DetailsVehiculeFragment()
    }

    private lateinit var viewmodel: DetailsVehiculeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_vehicule, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val vm = ViewModelProvider(this).get(Vehicule::class.java)
       // val vm= viewmodel.vehicule.value

        if (vm != null) {
            nomVehiculeD.text = vm.marque + vm.modele
            nbPlacesVehiculeD.text = vm.nbPlaces.toString()
            VehiculeImageViewD.setImageResource(vm.imageVehicule)
            modeleVehiculeD.text = vm.modele
            marqueVehiculeD.text = vm.marque
            capaciteMaxD.text = vm.capacite_max
            puissanceFiscaleD.text = vm.puissance_fiscale
            typeEnergieD.text = vm.type_energie
            vitesseMaxD.text = vm.vitesse_max
            goBackButtonDetailsVehicule.setOnClickListener{ this.findNavController().navigate(R.id.action_DetailsVehiculeFragment_pop)

            }

        }





    }



}

