package com.sil1.autolibdz_rental.ui.view.fragment.map_display

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sil1.autolibdz_rental.data.model.Borne
import com.sil1.autolibdz_rental.data.repositories.BorneRepository
import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.sil1.autolibdz_rental.data.model.PaymentIntent
import com.sil1.autolibdz_rental.data.repositories.LocataireRepository

class MapDisplayViewModel(val token:String) : ViewModel() {
    private val TAG = "TAG-MapDisplayViewModel"

    private val _valide = MutableLiveData<Int>()
    val valide: LiveData<Int>
        get() = _valide

    var bornes: MutableLiveData<ArrayList<Borne>> = MutableLiveData<ArrayList<Borne>>()

    init {
        Log.i("TAG", "\n=========\n token")
        bornes = BorneRepository.getAllBornes(TAG,"Basic $token")

    }

     fun getAllBornes(){
         bornes = BorneRepository.getAllBornes(TAG,"Basic $token")

     }

    fun getIdentitesLocataires(userID: String) {
        LocataireRepository.getIdentiteLocataire(TAG, token, userID){
            Log.i(TAG,"locataire est valide : $it.valide")
            if (it != null) {
                _valide.postValue(it.valide)
            }

        }
    }
}

