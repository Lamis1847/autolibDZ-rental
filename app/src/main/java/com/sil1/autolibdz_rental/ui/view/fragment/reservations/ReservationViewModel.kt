package com.sil1.autolibdz_rental.ui.view.fragment.reservations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sil1.autolibdz_rental.data.model.Reservation
import com.sil1.autolibdz_rental.data.repositories.ReservationRepository
import com.sil1.autolibdz_rental.utils.reservations

class ReservationViewModel : ViewModel() {
    private val TAG = "TAG-reservation-View-Model"



    fun getReservations(token:String?,id:String?) {
        reservations = ReservationRepository.getReservations(TAG, token,id)
    }



}