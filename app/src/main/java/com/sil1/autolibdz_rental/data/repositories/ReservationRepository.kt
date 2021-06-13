package com.sil1.autolibdz_rental.data.repositories

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.sil1.autolibdz_rental.data.api.ServiceBuilder
import com.sil1.autolibdz_rental.data.api.ServiceProvider
import com.sil1.autolibdz_rental.data.model.ReservationModel
import com.sil1.autolibdz_rental.data.model.ReservationResponse
import com.sil1.autolibdz_rental.data.model.VehiculeModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReservationRepository {
    companion object {
        val api: ServiceProvider by lazy {
            ServiceBuilder.buildService(ServiceProvider::class.java)
        }

         fun  ajouterReservation(
             TAG: String,
            reservation: ReservationModel,
            onResult: (MutableLiveData<ReservationResponse>?) -> Unit
         )  {
             var reserver = MutableLiveData<ReservationResponse>()
            val addReservationRequest = api.ajouterReservation(reservation)// consommation de l'api
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
                        onResult(null)
                      /*  Toast.makeText(
                            context,
                            "Le id est: " + message.codePin,
                            Toast.LENGTH_LONG
                        ).show();*/

                    } else {
                        val resp = response.body()
                        /*Toast.makeText(
                            context,
                            resp?.codePin.toString(),
                            Toast.LENGTH_SHORT
                        ).show()*/
                        reserver.value = resp!!
                        onResult(reserver)
                    }
                }

                override fun onFailure(call: Call<ReservationResponse>,  t: Throwable) {
                    /*Toast.makeText(
                        context,
                        "JE SUIS LA" + t.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()*/
                    onResult(null)

                }
            })


        }
    }
}