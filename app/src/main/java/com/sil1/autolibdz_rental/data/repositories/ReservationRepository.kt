package com.sil1.autolibdz_rental.data.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sil1.autolibdz_rental.data.api.ServiceBuilder
import com.sil1.autolibdz_rental.data.api.ServiceProvider
import com.sil1.autolibdz_rental.data.model.Reservation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReservationRepository {

    companion object {

        val api: ServiceProvider by lazy {
            ServiceBuilder.buildService(ServiceProvider::class.java)
        }


        fun getReservations(TAG: String,id:String): MutableLiveData<ArrayList<Reservation>> {
            var call = api.getReservations(id) // consommation de l'api
            var reservationRespond: List<Reservation>?
            var reservationList = ArrayList<Reservation>()
            var finalList = MutableLiveData<ArrayList<Reservation>>()

            call.enqueue(object : Callback<List<Reservation>> {
                override fun onResponse(call: Call<List<Reservation>>, response: Response<List<Reservation>>) {
                    Log.i(TAG, "Display reservation List: call enqueue")

                    if (!response.isSuccessful) {
                        Log.i(TAG, "CODE:" + response.code().toString())
                        return
                    }

                    reservationRespond = response.body()  // Getting the list
                    if (reservationRespond != null) {
                        Log.i(TAG, "REPONSES: HERE is ALL THE reservations:")
                        for (m in reservationRespond!!) {
                            var content = ""
                            content += " $m \n"
                            Log.i(TAG, "\n=========\n$content")
                            reservationList.add(m)
                        }
                        finalList.value = reservationList
                        //Log.i("TAG",marqueList.toString())
                    }
                }

                override fun onFailure(call: Call<List<Reservation>>, t: Throwable) {
                    Log.i(TAG, "error CODE:" + t.message)
                }
            })
            return finalList
        }

    }
}