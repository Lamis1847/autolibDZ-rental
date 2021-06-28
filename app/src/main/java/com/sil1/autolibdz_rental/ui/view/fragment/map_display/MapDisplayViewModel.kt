package com.sil1.autolibdz_rental.ui.view.fragment.map_display

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sil1.autolibdz_rental.data.model.Borne
import com.sil1.autolibdz_rental.data.repositories.BorneRepository


class MapDisplayViewModel : ViewModel() {
    private val TAG = "TAG-MapDisplayViewModel"

    var bornes: MutableLiveData<ArrayList<Borne>> = MutableLiveData<ArrayList<Borne>>()

    init {
        bornes = BorneRepository.getAllBornes(TAG)
    }

     fun getAllBornes(){
         bornes = BorneRepository.getAllBornes(TAG)
     }
}