package com.sil1.autolibdz_rental.ui.view.fragment.stripe_card

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sil1.autolibdz_rental.data.model.PaymentIntent
import com.sil1.autolibdz_rental.data.model.Transaction
import com.sil1.autolibdz_rental.data.repositories.AbonnementRepository
import com.sil1.autolibdz_rental.data.repositories.StripeRepository
import com.sil1.autolibdz_rental.utils.sharedPrefFile
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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

    fun createPaymentIntent(prix: Int, context: Context, idReservation: Int){
        StripeRepository.createPaymentIntent(TAG , prix) {
            Log.i(TAG, "view model here")
            _paymentIntent.postValue(it)
            if (it != null)
                createTransaction(context, idReservation )
        }
    }

    fun updateExpirationDate(cal: Calendar?) {
        if (cal!=null)
            _cardExpirationDate.postValue(cal!!)
    }

    private fun createTransaction(context: Context, idReservation: Int) {
        val sharedPref = context.getSharedPreferences(
            sharedPrefFile, Context.MODE_PRIVATE
        )

        val token = sharedPref.getString("token", "default")!!
        val id = sharedPref.getString("userID", "1")!!.toInt()

        var currentDateTime= LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("dd-MM HH:mm")
        val formatted = currentDateTime.format(formatter)

        val transaction = Transaction(id, idReservation,null, prixAPayer.toFloat(), "Stripe")

        AbonnementRepository.createTransaction(TAG, token, transaction) {}
    }
}