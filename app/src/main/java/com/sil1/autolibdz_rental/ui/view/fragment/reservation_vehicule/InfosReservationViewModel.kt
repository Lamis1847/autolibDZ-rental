package com.sil1.autolibdz_rental.ui.view.fragment.reservation_vehicule

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sil1.autolibdz_rental.data.model.ReservationModel
import com.sil1.autolibdz_rental.data.model.ReservationResponse
import com.sil1.autolibdz_rental.data.model.VehiculeModel
import com.sil1.autolibdz_rental.data.repositories.ReservationRepository
import com.sil1.autolibdz_rental.data.repositories.VehiculeRepository

class InfosReservationViewModel: ViewModel() {
    private val TAG = "TAG-InfosReservationViewModel"
    var reservation=  MutableLiveData<ReservationResponse>()

    fun  ajouterReservation(res : ReservationModel)  {
        ReservationRepository.ajouterReservation(TAG,res) {
            Log.i(TAG, "view model here")
            reservation.value = it?.value
        }

    }
}