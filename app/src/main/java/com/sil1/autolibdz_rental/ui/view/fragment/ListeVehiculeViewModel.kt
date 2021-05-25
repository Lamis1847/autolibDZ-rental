package com.sil1.autolibdz_rental.ui.view.fragment

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sil1.autolibdz_rental.ui.viewmodel.Vehicule
import com.sil1.autolibdz_rental.data.repositories.VehiculeRepository
import com.sil1.autolibdz_rental.ui.view.activity.ListeVehiculeFragment
import kotlin.coroutines.coroutineContext

class ListeVehiculeViewModel : ViewModel(){
    private val TAG = "TAG-ListeVehiculeDisplayViewModel"

    var vehicules: MutableLiveData<ArrayList<Vehicule>> = MutableLiveData<ArrayList<Vehicule>>()

    init {
        vehicules = VehiculeRepository.getListeVehicules(TAG)

      // Log.i(TAG, "C LA QU ON TIREEEEEEE LES DONNNNNNNNNNNNNNNEEEEES $vehicules")

    }

    fun getListeVehicule(){
        vehicules = VehiculeRepository.getListeVehicules(TAG)

    }
}