package com.sil1.autolibdz_rental.data.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sil1.autolibdz_rental.data.api.ServiceBuilder
import com.sil1.autolibdz_rental.data.api.ServiceProvider
import com.sil1.autolibdz_rental.data.model.VehiculeModel
import com.sil1.autolibdz_rental.ui.viewmodel.Vehicule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VehiculeRepository {

    companion object {

        val api: ServiceProvider by lazy {
            ServiceBuilder.buildService(ServiceProvider::class.java)
        }



        fun getListeVehicules(
            TAG: String,
            id:String,
            token : String,
            onResult: (MutableLiveData<ArrayList<VehiculeModel>>?) -> Unit
        ) {
            val call = api.getListeVehicules(id,token)

            call.enqueue(object : Callback<List<VehiculeModel>> {
                override fun onResponse(
                    call: Call<List<VehiculeModel>>,
                    response: Response<List<VehiculeModel>>
                ) {
                    if (!response.isSuccessful) {
                        Log.i(TAG, "CODE:" + response.code().toString())
                        onResult(null)
                    }

                    val vehiculeList = ArrayList<VehiculeModel>()
                    val finalList = MutableLiveData<ArrayList<VehiculeModel>>()
                    val vehiculeRespond = response.body()
                    if (vehiculeRespond != null) {
                        for (m in vehiculeRespond!!) {
                            Log.i(TAG, "Vehicule latitude:${m.latitude} longitude:${m.longitude}")

                            vehiculeList.add(m)
                        }
                        finalList.value = vehiculeList
                        onResult(finalList)
                    }
                }

                override fun onFailure(call: Call<List<VehiculeModel>>, t: Throwable) {
                    Log.i(TAG, "error CODE:" + t.message)
                    onResult(null)
                }
            })

        }





    }
}
