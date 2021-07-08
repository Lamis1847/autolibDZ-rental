package com.sil1.autolibdz_rental.ui.view.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.ui.viewmodel.Reservation
import com.sil1.autolibdz_rental.ui.viewmodel.Vehicule
import kotlinx.android.synthetic.main.fragment_details_vehicule.*
import kotlinx.android.synthetic.main.vehicule_layout.*


class DetailsVehiculeFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_details_vehicule, container, false)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        // myDrawerController?.setDrawer_UnLocked()
    }
    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val vm = ViewModelProvider(requireActivity()).get(Vehicule::class.java)
        val vmRes = ViewModelProvider(requireActivity()).get(Reservation::class.java)
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
        Glide.with(requireActivity()).load(vm.secureUrl).into(VehiculeImageViewD)
        reseverButtonD.setOnClickListener {
            findNavController().navigate(R.id.action_detailsVehiculeFragment_to_infosReservationFragment)
        }
        goBackButtonDetailsVehicule.setOnClickListener{ this.findNavController().navigate(R.id.action_DetailsVehiculeFragment_pop)
        }
            annulerButtonD.setOnClickListener{ this.findNavController().navigate(R.id.action_DetailsVehiculeFragment_pop)
        }

    }
}

