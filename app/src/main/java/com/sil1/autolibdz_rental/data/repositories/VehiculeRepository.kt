package com.sil1.autolibdz_rental.data.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sil1.autolibdz_rental.data.api.ServiceBuilder
import com.sil1.autolibdz_rental.data.api.ServiceProvider
import com.sil1.autolibdz_rental.ui.viewmodel.Vehicule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VehiculeRepository {

    companion object {

        val api: ServiceProvider by lazy {
            ServiceBuilder.buildService(ServiceProvider::class.java)
        }


        /*fun getVehiculeDetails(TAG: String, id:Int): MutableLiveData<Vehicule> {
            var call = api.getVehiculeDetails(Int) // consommation de l'api
            var vehiculeRespond: Vehicule?
            vehiculeRespond = Vehicule(0,"","","",0,0,"","","","")
            //var borneList = ArrayList<Borne>()
           var finalVehicule = MutableLiveData<Vehicule>()

            call.enqueue(object : Callback<Vehicule> {
                override fun onResponse(
                    call: Call<Vehicule>,
                    response: Response<Vehicule>
                ) {
                    Log.i(TAG, "Display Vehicule Details: call enqueue")

                    if (!response.isSuccessful) {
                        Log.i(TAG, "CODE:" + response.code().toString())
                        return
                    }

                    vehiculeRespond = response.body()  // Getting the details
                    if (vehiculeRespond != null) {
                        Log.i(TAG, "REPONSES: HERE is ALL THE VEHICULE DETAILS:")

                            Log.i(TAG, "\n=========\n$vehiculeRespond")
                        finalVehicule.value = vehiculeRespond
                    }
                }

                override fun onFailure(call: Call<Vehicule>, t: Throwable) {
                    Log.i(TAG, "error CODE:" + t.message)
                }


            })
            return finalVehicule
        }*/


        fun getListeVehicules(TAG: String): MutableLiveData<ArrayList<Vehicule>> {
            var call = api.getListeVehicules() // consommation de l'api

            var vehiculeRespond: List<Vehicule>?
            var vehiculeList = ArrayList<Vehicule>()
            var finalList = MutableLiveData<ArrayList<Vehicule>>()

            call.enqueue(object : Callback<List<Vehicule>> {
                override fun onResponse(call: Call<List<Vehicule>>, response: Response<List<Vehicule>>) {

                    if (!response.isSuccessful) {
                        Log.i(TAG, "CODE:" + response.code().toString())
                        return
                    }

                    vehiculeRespond = response.body()  // Getting the list

                    if (vehiculeRespond != null) {
                      //  Log.i(TAG, "REPONSES: HERE is ALL THE VEHICULES:")
                        for (m in vehiculeRespond!!) {
                           /* var content = ""
                            content += " $m \n"
                            Log.i(TAG, "\n=========\n$content")*/

                            vehiculeList.add(m)
                        }
                        finalList.value = vehiculeList

                    }
                }

                override fun onFailure(call: Call<List<Vehicule>>, t: Throwable) {
                    Log.i(TAG, "error CODE:" + t.message)
                }
            })
            Log.i(TAG, "error CODE:" + finalList?.value!![0]?.marque)

            return finalList
        }




    }
}