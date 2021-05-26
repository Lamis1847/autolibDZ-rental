package com.sil1.autolibdz_rental.ui.view.fragment.profil

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sil1.autolibdz_rental.data.model.LocataireRetro
import com.sil1.autolibdz_rental.data.repositories.LocataireRepository

class ProfilViewModel : ViewModel() {
    private val TAG = "TAG-ProfilViewModel"

    var locataire: MutableLiveData<ArrayList<LocataireRetro?>> = MutableLiveData<ArrayList<LocataireRetro?>>()

    init {
        locataire = LocataireRepository.getLocataire(TAG)
    }


}