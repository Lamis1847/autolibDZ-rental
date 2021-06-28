package com.sil1.autolibdz_rental.ui.view.fragment.abonnement_payment

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sil1.autolibdz_rental.data.model.Balance
import com.sil1.autolibdz_rental.data.model.Transaction
import com.sil1.autolibdz_rental.data.repositories.AbonnementRepository
import com.sil1.autolibdz_rental.data.repositories.StripeRepository
import com.sil1.autolibdz_rental.utils.sharedPrefFile
import okhttp3.ResponseBody
import retrofit2.Response

class AbonnementPaymentViewModel : ViewModel() {
    private val TAG = "TAG-AbonnementPaymentViewModel"
    var prixAPayer: Double = 0.0

    private val _balance = MutableLiveData<Balance>()
    val balance: LiveData<Balance>
        get() = _balance

    private val _response = MutableLiveData<Response<ResponseBody>>()
    val response: LiveData<Response<ResponseBody>>
        get() = _response

    fun getUserBalance(context: Context) {
        val sharedPref = context.getSharedPreferences(
            sharedPrefFile, Context.MODE_PRIVATE
        )

        val token = sharedPref.getString("token", "default")!!
        val id = sharedPref.getString("userID", "1")!!.toInt()

        AbonnementRepository.getUserBalance(TAG, token, id) {
            Log.i(TAG, "view model here")
            _balance.postValue(it)
        }
    }

    fun payWithAbonnement(context: Context) {
        val sharedPref = context.getSharedPreferences(
            sharedPrefFile, Context.MODE_PRIVATE
        )

        val token = sharedPref.getString("token", "default")!!
        val id = sharedPref.getString("userID", "1")!!.toInt()

        AbonnementRepository.payWithAbonnement(TAG, token, id,prixAPayer) {
            Log.i(TAG, "view model here (checkout)")
            _response.postValue(it)
            if (it?.message()=="OK")
                createTransaction(token, id)
        }
    }

    private fun createTransaction(token:String, id: Int) {
        //val idReservation = 1

        //val transaction = Transaction(id, idReservation,prixAPayer, "Paiement Carte d\'abonnement")

        //AbonnementRepository.createTransaction(TAG, token, transaction) {}
    }
}
