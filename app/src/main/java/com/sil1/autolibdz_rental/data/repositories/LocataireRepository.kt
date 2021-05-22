package com.sil1.autolibdz_rental.data.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sil1.autolibdz_rental.data.api.ServiceBuilder
import com.sil1.autolibdz_rental.data.api.ServiceProvider
import com.sil1.autolibdz_rental.data.model.LocataireRetro
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocataireRepository {

    companion object {

        val api: ServiceProvider by lazy {
            ServiceBuilder.buildService(ServiceProvider::class.java)
        }


        fun getLocataire(TAG: String): MutableLiveData<ArrayList<LocataireRetro?>> {
            var call = api.getLocataire() // consommation de l'api
            var locataireRespond: LocataireRetro?
            var locatairelist = ArrayList<LocataireRetro?>()
            var finalList = MutableLiveData<ArrayList<LocataireRetro?>>()
            call.enqueue(object : Callback<LocataireRetro> {
                override fun onResponse(
                    call: Call<LocataireRetro>,
                    response: Response<LocataireRetro>
                ) {

                    Log.i(TAG, "Display locataire List: call enqueue")

                    if (!response.isSuccessful) {
                        Log.i(TAG, "CODE:" + response.code().toString())
                        return
                    }

                    locataireRespond = response.body()  // Getting the list
                    if (locataireRespond != null) {
                        Log.i(TAG, "REPONSES: HERE is locataire:")
                        locatairelist.add(locataireRespond)
                    }
                    //borneList.sortBy { it.name }
                    finalList.value = locatairelist
                    //Log.i("TAG",marqueList.toString())
                }


                override fun onFailure(call: Call<LocataireRetro>, t: Throwable) {

                    Log.i(TAG, "error CODE:" + t.message)
                }
            })
            return finalList
        }

    }

}


