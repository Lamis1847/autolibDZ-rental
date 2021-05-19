package com.sil1.autolibdz_rental.data.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sil1.autolibdz_rental.data.api.ServiceBuilder
import com.sil1.autolibdz_rental.data.api.ServiceProvider
import com.sil1.autolibdz_rental.data.model.Borne
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BorneRepository {

    companion object {

        val api: ServiceProvider by lazy {
            ServiceBuilder.buildService(ServiceProvider::class.java)
        }


        fun getAllBornes(TAG: String): MutableLiveData<ArrayList<Borne>> {
            var call = api.getAllBornes() // consommation de l'api
            var borneRespond: List<Borne>?
            var borneList = ArrayList<Borne>()
            var finalList = MutableLiveData<ArrayList<Borne>>()

            call.enqueue(object : Callback<List<Borne>> {
                override fun onResponse(call: Call<List<Borne>>, response: Response<List<Borne>>) {
                    Log.i(TAG, "Display Borne List: call enqueue")

                    if (!response.isSuccessful) {
                        Log.i(TAG, "CODE:" + response.code().toString())
                        return
                    }

                    borneRespond = response.body()  // Getting the list
                    if (borneRespond != null) {
                        Log.i(TAG, "REPONSES: HERE is ALL THE BORNES:")
                        for (m in borneRespond!!) {
                            var content = ""
                            content += " $m \n"
                            Log.i(TAG, "\n=========\n$content")
                            borneList.add(m)
                        }
                        //borneList.sortBy { it.name }
                        finalList.value = borneList
                        //Log.i("TAG",marqueList.toString())
                    }
                }

                override fun onFailure(call: Call<List<Borne>>, t: Throwable) {
                    Log.i(TAG, "error CODE:" + t.message)
                }
            })
            return finalList
        }

    }
}