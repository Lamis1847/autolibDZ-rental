package com.sil1.autolibdz_rental.ui.view.fragment.reservation_vehicule

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class InfosReservationViewModelFactory (val token : String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InfosReservationViewModel::class.java)) {
            Log.i("Factory",token)
            return InfosReservationViewModel(token) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}

