package com.sil1.autolibdz_rental.ui.view.fragment.reservation_vehicule

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sil1.autolibdz_rental.data.model.*
import com.sil1.autolibdz_rental.data.repositories.ReclamationRepository
import com.sil1.autolibdz_rental.data.repositories.ReservationRepository
import com.sil1.autolibdz_rental.data.repositories.VehiculeRepository

class ReclamationViewModel: ViewModel() {
    private val TAG = "TAG-ReclamationViewModel"
    var reclamation=  MutableLiveData<ReclamationResponse>()

    fun  ajouterReclamation(res : ReclamationModel)  {
        ReclamationRepository.ajouterReclamation(TAG,res) {
            Log.i(TAG, "view model here")
            reclamation.value = it?.value
        }

    }
}