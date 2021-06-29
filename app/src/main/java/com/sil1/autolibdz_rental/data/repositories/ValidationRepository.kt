package com.sil1.autolibdz_rental.data.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sil1.autolibdz_rental.data.api.ServiceBuilder
import com.sil1.autolibdz_rental.data.api.ServiceProvider
import com.sil1.autolibdz_rental.data.model.ValidationBody
import com.sil1.autolibdz_rental.data.model.VehiculeModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ValidationRepository {

    companion object {

        val api: ServiceProvider by lazy {
            ServiceBuilder.buildService(ServiceProvider::class.java)
        }

        val callResult:MutableLiveData<Boolean> = MutableLiveData()

        fun envoyerValidationDemande(
            idLocataire: Int,
            selfie:String,
            photo:String,
            idPhoto:String,
            idSelfie:String,
            onResult: (MutableLiveData<Boolean>?) -> Unit
        ) {
            val validationBody = ValidationBody(idLocataire,selfie,photo,idPhoto,idSelfie)
            val call = api.envoyerValidationDemande(validationBody)

            call.enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {

                    Log.e("validation-call-ok",response.body()!!.toString())
                    callResult.value = true
                    onResult(callResult)
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e("validation-call-error",t.toString())

                    callResult.value = false
                    onResult(callResult)
                }
            })

        }





    }
}