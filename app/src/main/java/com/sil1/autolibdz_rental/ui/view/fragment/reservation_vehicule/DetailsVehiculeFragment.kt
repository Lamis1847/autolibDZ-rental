package com.sil1.autolibdz_rental.ui.view.activity

import android.annotation.SuppressLint
import android.net.Uri
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
import kotlinx.android.synthetic.main.vehicule_layout.*


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
            marqueD.text = vm.marque
            etatVehiculeD.text = vm.etat
            numeroChassisD.text = vm.numChassis.toString()
            modeleD.text = vm.modele
            couleurD.text = vm.couleur
            tempRD.text = vm.tempsDeRefroidissement.toString()
            pressionHD.text = vm.pressionHuileMoteur.toString()
            chargeD.text = vm.chargeBatterie.toString()
            pressionPneuD.text = vm.pressionPneus.toString()
            niveauHMD.text = vm.pressionHuileMoteur.toString()
            anomalieCD.text = vm.anomalieCircuit
            regulateurVD.text = vm.regulateurVitesse.toString()
            limiteurVD.text=vm.limiteurVitesse.toString()
          /*  if(vm.secureUrl != "") {
                val imgUri: Uri = Uri.parse(vm.secureUrl)
                VehiculeImageViewD.setImageURI(imgUri)
            }else{
            VehiculeImageViewD.setImageResource(R.drawable.vehiculeimg)
            }*/

            goBackButtonDetailsVehicule.setOnClickListener{ this.findNavController().navigate(R.id.action_DetailsVehiculeFragment_pop)

        }
    }
}

