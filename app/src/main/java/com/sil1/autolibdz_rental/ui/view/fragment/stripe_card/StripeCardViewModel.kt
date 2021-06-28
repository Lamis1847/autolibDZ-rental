package com.sil1.autolibdz_rental.ui.view.fragment.stripe_card

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sil1.autolibdz_rental.data.model.PaymentIntent
import com.sil1.autolibdz_rental.data.model.Transaction
import com.sil1.autolibdz_rental.data.repositories.AbonnementRepository
import com.sil1.autolibdz_rental.data.repositories.StripeRepository
import java.util.*

class StripeCardViewModel : ViewModel() {
    private val TAG = "TAG-StripeCardViewModel"
    private val _cardExpirationDate = MutableLiveData<Calendar>()
    val cardExpirationDate : LiveData<Calendar>
        get() = _cardExpirationDate

    private val _paymentIntent = MutableLiveData<PaymentIntent>()
    val paymentIntent: LiveData<PaymentIntent>
        get() = _paymentIntent

    var prixAPayer: Double = 0.0

    fun createPaymentIntent(prix: Int){
        StripeRepository.createPaymentIntent(TAG , prix) {
            Log.i(TAG, "view model here")
            _paymentIntent.postValue(it)

            //createTransaction()
        }
    }

    fun updateExpirationDate(cal: Calendar?) {
        if (cal!=null)
            _cardExpirationDate.postValue(cal!!)
    }

    private fun createTransaction() {
        val id = 1
        val idReservation = 2

        //val transaction = Transaction(id, idReservation,prixAPayer, "Stripe")

        //AbonnementRepository.createTransaction(TAG, transaction) {}
    }
}