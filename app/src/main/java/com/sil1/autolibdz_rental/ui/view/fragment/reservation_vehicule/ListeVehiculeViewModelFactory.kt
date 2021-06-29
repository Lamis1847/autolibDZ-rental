package com.sil1.autolibdz_rental.ui.view.fragment.reservation_vehicule
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sil1.autolibdz_rental.ui.view.fragment.ListeVehiculeViewModel

class ListeVehiculeViewModelFactory(val token : String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListeVehiculeViewModel::class.java)) {
            Log.i("Factory",token)
            return ListeVehiculeViewModel(token) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}

