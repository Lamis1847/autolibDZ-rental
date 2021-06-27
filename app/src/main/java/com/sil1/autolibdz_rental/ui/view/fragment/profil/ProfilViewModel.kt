package com.sil1.autolibdz_rental.ui.view.fragment.profil

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sil1.autolibdz_rental.data.model.LocataireEditEmail
import com.sil1.autolibdz_rental.data.model.LocataireRetro
import com.sil1.autolibdz_rental.data.repositories.LocataireRepository
import com.sil1.autolibdz_rental.utils.sharedPrefFile

class ProfilViewModel : ViewModel() {
    private val TAG = "TAG-ProfilViewModel"


    private var id:String="1"; //changer cet id selon l'utilisateur

    var locataire = MutableLiveData<ArrayList<LocataireRetro?>>()

   fun getLocataire(userID:String) {
       locataire = LocataireRepository.getLocataire(TAG, userID)
   }
    fun updateLocataire(locataireEdit: LocataireEditEmail, id:String):String {
        return LocataireRepository.editLocataire(TAG,id,locataireEdit)

    }


}