package com.sil1.autolibdz_rental.ui.view.fragment

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sil1.autolibdz_rental.data.model.VehiculeModel
import com.sil1.autolibdz_rental.ui.viewmodel.Vehicule
import com.sil1.autolibdz_rental.data.repositories.VehiculeRepository
import com.sil1.autolibdz_rental.ui.view.activity.ListeVehiculeFragment
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.delay as delay

class ListeVehiculeViewModel : ViewModel(){
    private val TAG = "TAG-ListeVehiculeDisplayViewModel"
    var vehicules: MutableLiveData<ArrayList<VehiculeModel>> ?= null

     fun  getListeVehicule() {
        VehiculeRepository.getListeVehicules(TAG){
            Log.i(TAG, "view model here")
            vehicules = it
            Log.i(TAG, "view model here")
        }
    }
}
