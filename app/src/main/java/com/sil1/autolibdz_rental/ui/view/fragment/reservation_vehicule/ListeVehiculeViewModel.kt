package com.sil1.autolibdz_rental.ui.view.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sil1.autolibdz_rental.data.model.VehiculeModel
import com.sil1.autolibdz_rental.data.repositories.VehiculeRepository


class ListeVehiculeViewModel(token:String) : ViewModel(){
    private val TAG = "TAG-ListeVehiculeDisplayViewModel"
    var vehicules=  MutableLiveData<ArrayList<VehiculeModel>>()

     fun  getListeVehicule(id : String,token:String)  {
        VehiculeRepository.getListeVehicules(TAG,id,"Basic $token") {
            Log.i(TAG, "view model here")
            vehicules.value = it?.value
        }

    }
}
