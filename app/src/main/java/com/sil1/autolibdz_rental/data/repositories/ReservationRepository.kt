package com.sil1.autolibdz_rental.data.repositories

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.sil1.autolibdz_rental.data.api.ServiceBuilder
import com.sil1.autolibdz_rental.data.api.ServiceProvider
import com.sil1.autolibdz_rental.data.model.ReservationModel
import com.sil1.autolibdz_rental.data.model.ReservationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReservationRepository {
    companion object {

        val api: ServiceProvider by lazy {
            ServiceBuilder.buildService(ServiceProvider::class.java)
        }

        fun ajouterReservation(
            context: Context,
            reservation: ReservationModel
        ) {
            val addReservationRequest = api.ajouterReservation(reservation)
            // consommation de l'api
            addReservationRequest.enqueue(object : Callback<ReservationResponse> {
                override fun onResponse(
                    call: Call<ReservationResponse>,
                    response: Response<ReservationResponse>
                ) {
                    if (!response.isSuccessful()) {
                        val gson = Gson()
                        val message: ReservationResponse = gson.fromJson(
                            response.errorBody()!!.charStream(),
                            ReservationResponse::class.java
                        )
                        Toast.makeText(
                            context,
                            "Le id est: " + message.id,
                            Toast.LENGTH_LONG
                        ).show();

                    } else {
                        val resp = response.body()
                        Toast.makeText(
                            context,
                            resp?.id.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ReservationResponse>, t: Throwable) {
                    Toast.makeText(
                        context,
                        "JE SUIS LA" + t.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("TEST",t.localizedMessage )
                }
            })

        }
    }
}