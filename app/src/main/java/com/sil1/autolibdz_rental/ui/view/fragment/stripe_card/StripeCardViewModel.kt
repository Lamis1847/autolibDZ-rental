package com.sil1.autolibdz_rental.ui.view.fragment.stripe_card

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sil1.autolibdz_rental.data.model.PaymentIntent
import com.sil1.autolibdz_rental.data.repositories.StripeRepository

class StripeCardViewModel : ViewModel() {
    private val TAG = "TAG-StripeCardViewModel"

    private val _paymentIntent = MutableLiveData<PaymentIntent>()
    val paymentIntent: LiveData<PaymentIntent>
        get() = _paymentIntent

    var prixAPayer: Double? = null

    fun createPaymentIntent(prix: Int){
        StripeRepository.createPaymentIntent(TAG , prix) {
            Log.i(TAG, "view model here")
            _paymentIntent.postValue(it)
        }
    }
}