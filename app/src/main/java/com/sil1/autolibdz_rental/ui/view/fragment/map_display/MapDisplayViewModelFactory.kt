package com.sil1.autolibdz_rental.ui.view.fragment.map_display
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MapDisplayViewModelFactory (val token : String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapDisplayViewModel::class.java)) {
            Log.i("Factory",token)
            return MapDisplayViewModel(token) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}
