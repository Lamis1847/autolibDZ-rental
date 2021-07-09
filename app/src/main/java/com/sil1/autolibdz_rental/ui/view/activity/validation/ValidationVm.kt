package com.sil1.autolibdz_rental.ui.view.activity.validation


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sil1.autolibdz_rental.data.model.ValidationBody
import com.sil1.autolibdz_rental.data.repositories.ValidationRepository


class ValidationVm : ViewModel() {


    var callResult = MutableLiveData<Boolean>()

    fun envoyerValidationDemande(idLocataire: Int, selfie:String, photo:String,idPhoto:String, idSelfie:String) {

         ValidationRepository.envoyerValidationDemande(idLocataire,selfie,photo,idPhoto,idSelfie){
             callResult.value = it!!.value
        }
    }



}