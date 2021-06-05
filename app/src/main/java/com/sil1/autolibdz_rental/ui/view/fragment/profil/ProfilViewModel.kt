package com.sil1.autolibdz_rental.ui.view.fragment.profil

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sil1.autolibdz_rental.data.model.LocataireRetro
import com.sil1.autolibdz_rental.data.repositories.LocataireRepository

class ProfilViewModel : ViewModel() {
    private val TAG = "TAG-ProfilViewModel"
   private var id:String="193"; //changer cet id selon l'utilisateur

    var locataire = MutableLiveData<ArrayList<LocataireRetro?>>()

   fun getLocataire() {
       locataire = LocataireRepository.getLocataire(TAG, id)
   }
    fun updateLocataire(locataireEdit:LocataireRetro,id:String):Boolean {
        return LocataireRepository.editLocataire(TAG,id,locataireEdit)

    }


}