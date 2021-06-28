package com.sil1.autolibdz_rental.data.repositories

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.sil1.autolibdz_rental.data.api.ServiceBuilder
import com.sil1.autolibdz_rental.data.api.ServiceProvider
import com.sil1.autolibdz_rental.data.model.ReclamationModel
import com.sil1.autolibdz_rental.data.model.ReclamationResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReclamationRepository {
    companion object {
        val api: ServiceProvider by lazy {
            ServiceBuilder.buildService(ServiceProvider::class.java)
        }

        fun  ajouterReclamation(
            TAG: String,
            reclamation: ReclamationModel,
            onResult: (MutableLiveData<ReclamationResponse>?) -> Unit

        )  {
            var reclamer = MutableLiveData<ReclamationResponse>()
            val addReclamationRequest = api.ajouterReclamation(reclamation)// consommation de l'api
            addReclamationRequest.enqueue(object : Callback<ReclamationResponse> {
                override fun onResponse(
                    call: Call<ReclamationResponse>,
                    response: Response<ReclamationResponse>
                ) {
                    if (!response.isSuccessful()) {
                        val gson = Gson()
                        val message: ReclamationResponse = gson.fromJson(
                            response.errorBody()!!.charStream(),
                            ReclamationResponse::class.java
                        )
                        onResult(null)
                        Log.i(TAG, "ERROR"+message+ "reclam"+ reclamation)


                    } else {
                        val resp = response.body()

                        reclamer.value = resp!!
                        onResult(reclamer)
                        Log.i(TAG, "La réclamation a été envoyée correctement"+ reclamer.value )

                    }
                }

                override fun onFailure(call: Call<ReclamationResponse>, t: Throwable) {

                    Log.i(TAG, "Erreur lors de l'envoie"+t.localizedMessage)
                    onResult(null)
                }
            })


        }
    }
}