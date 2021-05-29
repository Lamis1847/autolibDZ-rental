package com.sil1.autolibdz_rental.ui.view.fragment.profil

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sil1.autolibdz_rental.data.model.Reservation
import com.sil1.autolibdz_rental.data.repositories.ReservationRepository

class ReservationViewModel : ViewModel() {
    private val TAG = "TAG-reservation-View-Model"
    private var id:String="3"; //changer cet id selon l'utilisateur

    var reservations = MutableLiveData<ArrayList<Reservation>>()

    fun getReservations() {
        reservations = ReservationRepository.getReservations(TAG, id)
    }



}