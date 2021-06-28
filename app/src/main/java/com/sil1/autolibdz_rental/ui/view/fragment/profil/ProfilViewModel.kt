package com.sil1.autolibdz_rental.ui.view.fragment.profil

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sil1.autolibdz_rental.data.model.LocataireEditEmail
import com.sil1.autolibdz_rental.data.model.LocataireEditPassword
import com.sil1.autolibdz_rental.data.model.LocataireRetro
import com.sil1.autolibdz_rental.data.repositories.LocataireRepository
import com.sil1.autolibdz_rental.utils.sharedPrefFile

class ProfilViewModel : ViewModel() {
    private val TAG = "TAG-ProfilViewModel"




    var locataire = MutableLiveData<ArrayList<LocataireRetro?>>()

   fun getLocataire(userID:String?) {
       locataire = LocataireRepository.getLocataire(TAG, userID)
   }
    fun updateMailLocataire(locataireEdit: LocataireEditEmail, id:String,context:Context) {
     LocataireRepository.editMailLocataire(TAG,id,locataireEdit,context)

    }

    fun updatePasswordLocataire(locataireEdit: LocataireEditPassword, id:String,context:Context) {
        LocataireRepository.editPasswordLocataire(TAG,id,locataireEdit,context)

    }

}