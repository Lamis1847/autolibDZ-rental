package com.sil1.autolibdz_rental.ui.view.fragment.map_display

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sil1.autolibdz_rental.data.model.Borne
import com.sil1.autolibdz_rental.data.repositories.BorneRepository
import android.app.Application
import android.content.Context

class MapDisplayViewModel(val token:String) : ViewModel() {
    private val TAG = "TAG-MapDisplayViewModel"

    var bornes: MutableLiveData<ArrayList<Borne>> = MutableLiveData<ArrayList<Borne>>()

    init {
        Log.i("TAG", "\n=========\n token")
        bornes = BorneRepository.getAllBornes(TAG,"Basic $token")

    }

     fun getAllBornes(){
         bornes = BorneRepository.getAllBornes(TAG,"Basic $token")

     }
}

