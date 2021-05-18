package com.sil1.autolibdz_rental.ui.view.fragment.map_display

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sil1.autolibdz_rental.data.api.Api
import com.sil1.autolibdz_rental.data.model.Borne
import retrofit2.Call
import retrofit2.Callback

class MapDisplayViewModel : ViewModel() {
    private val TAG = "TAG-MapDisplayViewModel"

    var bornes: MutableLiveData<ArrayList<Borne>> = MutableLiveData<ArrayList<Borne>>()
    init {
        bornes = Api.getAllBornes(TAG)

    }

     fun getAllBornes(){
         bornes = Api.getAllBornes(TAG)

     }
}