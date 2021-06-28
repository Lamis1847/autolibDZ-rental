package com.sil1.autolibdz_rental.data.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sil1.autolibdz_rental.data.api.ServiceBuilder
import com.sil1.autolibdz_rental.data.api.ServiceProvider
import com.sil1.autolibdz_rental.data.model.PaymentIntent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StripeRepository {

    companion object {

        val api: ServiceProvider by lazy {
            ServiceBuilder.buildService(ServiceProvider::class.java)
        }


        fun createPaymentIntent(TAG: String, prix: Int, onResult: (PaymentIntent?) -> Unit){
            var call = api.createPaymentIntent(prix)

            call.enqueue(object : Callback<PaymentIntent> {
                override fun onResponse(call: Call<PaymentIntent>, response: Response<PaymentIntent>) {
                    Log.i(TAG, "Creating payment intent response")

                    if (!response.isSuccessful) {
                        Log.i(TAG, "CODE:" + response.code().toString() + " Message: " + response.message())
                        onResult(null)
                    }

                    val paymentIntent = response.body()  // Getting the paymentIntent
                    onResult(paymentIntent)
                }


                override fun onFailure(call: Call<PaymentIntent>, t: Throwable) {
                    Log.i( TAG, "error CODE:" + t.message)
                    onResult(null)
                }
            })
        }

    }
}