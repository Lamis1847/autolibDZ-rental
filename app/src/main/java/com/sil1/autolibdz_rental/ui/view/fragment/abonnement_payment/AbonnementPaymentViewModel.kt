package com.sil1.autolibdz_rental.ui.view.fragment.abonnement_payment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sil1.autolibdz_rental.data.model.Balance
import com.sil1.autolibdz_rental.data.repositories.AbonnementRepository
import com.sil1.autolibdz_rental.data.repositories.StripeRepository

class AbonnementPaymentViewModel : ViewModel() {
    private val TAG = "TAG-AbonnementPaymentViewModel"
    var prixAPayer: Double = 0.0
    //var balance: MutableLiveData<Balance>? = null

    private val _balance = MutableLiveData<Balance>()
    val balance: LiveData<Balance>
        get() = _balance

    fun getUserBalance() {
        val id = 1

        AbonnementRepository.getUserBalance(TAG, id) {
            Log.i(TAG, "view model here")
            _balance.postValue(it)
        }
    }

    fun payWithAbonnement() {
        val id = 1

        AbonnementRepository.payWithAbonnement(TAG, id,prixAPayer) {
            Log.i(TAG, "view model here (checkout)")
        }
    }
}
