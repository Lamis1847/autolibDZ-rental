package com.sil1.autolibdz_rental.ui.view.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sil1.autolibdz_rental.data.model.VehiculeModel
import com.sil1.autolibdz_rental.data.repositories.VehiculeRepository


class ListeVehiculeViewModel : ViewModel(){
    private val TAG = "TAG-ListeVehiculeDisplayViewModel"
    var vehicules=  MutableLiveData<ArrayList<VehiculeModel>>()

     fun  getListeVehicule()  {
        VehiculeRepository.getListeVehicules(TAG) {
            Log.i(TAG, "view model here")
            vehicules.value = it?.value
        }

    }
}
