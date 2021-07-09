package com.sil1.autolibdz_rental.data.repositories

import android.util.Log
import com.sil1.autolibdz_rental.data.api.ServiceBuilder
import com.sil1.autolibdz_rental.data.api.ServiceProvider
import com.sil1.autolibdz_rental.data.model.Trajet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrajetRepository {
    companion object {

        val api: ServiceProvider by lazy {
            ServiceBuilder.buildService(ServiceProvider::class.java)
        }

        fun getTrajetByReservation(
            TAG: String,
            idReservation: Int,
            onResult: (Trajet?) -> Unit
        ) {
            var call = api.getTrajetByReservation(idReservation)

            call.enqueue(object : Callback<Trajet> {
                override fun onResponse(
                    call: Call<Trajet>,
                    response: Response<Trajet>
                ) {
                    if (!response.isSuccessful) {
                        Log.i(TAG, "CODE:" + response.code().toString())
                        onResult(null)
                    }
                    Log.i(TAG, response.toString())
                    onResult(response.body())
                }

                override fun onFailure(call: Call<Trajet>, t: Throwable) {
                    Log.i(TAG, "error CODE:" + t.message)
                    onResult(null)
                }
            })
        }

    }
}