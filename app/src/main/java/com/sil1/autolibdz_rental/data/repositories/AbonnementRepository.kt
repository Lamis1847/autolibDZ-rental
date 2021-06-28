package com.sil1.autolibdz_rental.data.repositories

import android.util.Log
import com.google.gson.annotations.SerializedName
import com.sil1.autolibdz_rental.data.api.ServiceBuilder
import com.sil1.autolibdz_rental.data.api.ServiceProvider
import com.sil1.autolibdz_rental.data.model.Balance
import com.sil1.autolibdz_rental.data.model.PaymentIntent
import com.sil1.autolibdz_rental.data.model.Transaction
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AbonnementRepository {

    companion object {

        val api: ServiceProvider by lazy {
            ServiceBuilder.buildService(ServiceProvider::class.java)
        }


        fun getUserBalance(TAG: String, token: String, id: Int, onResult: (Balance?) -> Unit){
            var call = api.getUserBalance("Basic $token", id)

            call.enqueue(object : Callback<Balance> {
                override fun onResponse(call: Call<Balance>, response: Response<Balance>) {
                    Log.i(TAG, "Getting user balance")

                    if (!response.isSuccessful) {
                        Log.i(TAG, "CODE:" + response.code().toString() + " Message: " + response.message())
                        onResult(null)
                    }
                    val userBalance = response.body()  // Getting the balance
                    Log.i(TAG, userBalance?.userBalance.toString())
                    onResult(userBalance)
                }


                override fun onFailure(call: Call<Balance>, t: Throwable) {
                    Log.i( TAG, "error CODE:" + t.message)
                    onResult(null)
                }
            })
        }

        fun payWithAbonnement(TAG: String, token:String , id: Int, amount: Double, onResult: (Response<ResponseBody>?) -> Unit){
            var call = api.payWithAbonnement("Basic $token", id, amount)

            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    Log.i(TAG, "subscription card checkout")

                    if (!response.isSuccessful) {
                        Log.i(TAG, "CODE:" + response.code().toString() + " Message: " + response.message())
                        onResult(null)
                    }
                    onResult(response)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.i( TAG, "error CODE:" + t.message)
                    onResult(null)
                }
            })
        }

        fun createTransaction(TAG: String, token: String, transaction: Transaction, onResult: (Response<ResponseBody>?) -> Unit) {
            var call = api.createTransaction( "Basic $token" ,transaction)

            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    Log.i(TAG, "Creating transaction")

                    if (!response.isSuccessful) {
                        Log.i(TAG, "CODE:" + response.code().toString() + " Message: " + response.message())
                        onResult(null)
                    }
                    onResult(response)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.i( TAG, "error CODE:" + t.message)
                    onResult(null)
                }
            })
        }

    }
}