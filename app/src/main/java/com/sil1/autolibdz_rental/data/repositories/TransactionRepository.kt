package com.sil1.autolibdz_rental.data.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sil1.autolibdz_rental.data.api.ServiceBuilder
import com.sil1.autolibdz_rental.data.api.ServiceProvider
import com.sil1.autolibdz_rental.data.model.Transaction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionRepository {
    companion object {

        val api: ServiceProvider by lazy {
            ServiceBuilder.buildService(ServiceProvider::class.java)
        }

        fun getUserTransactions(
            TAG: String,
            token: String,
            id: Int,
            onResult: (ArrayList<Transaction>?) -> Unit
        ) {
            var call = api.getUserTransactions(id, "Basic $token")

            call.enqueue(object : Callback<ArrayList<Transaction>> {
                override fun onResponse(
                    call: Call<ArrayList<Transaction>>,
                    response: Response<ArrayList<Transaction>>
                ) {
                    if (!response.isSuccessful) {
                        Log.i(TAG, "CODE:" + response.code().toString())
                        onResult(null)
                    }
                    Log.i(TAG, response.toString())
                    onResult(response.body())
                }

                override fun onFailure(call: Call<ArrayList<Transaction>>, t: Throwable) {
                    Log.i(TAG, "error CODE:" + t.message)
                    onResult(null)
                }
            })
        }

    }
}