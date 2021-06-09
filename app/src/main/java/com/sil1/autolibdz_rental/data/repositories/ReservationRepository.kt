package com.sil1.autolibdz_rental.data.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.work.PeriodicWorkRequestBuilder
import com.sil1.autolibdz_rental.data.api.ServiceBuilder
import com.sil1.autolibdz_rental.data.api.ServiceProvider
import com.sil1.autolibdz_rental.data.model.Reservation
import com.sil1.autolibdz_rental.data.model.ReservationRoom
import com.sil1.autolibdz_rental.data.room.RoomService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReservationRepository {

    companion object {

        val api: ServiceProvider by lazy {
            ServiceBuilder.buildService(ServiceProvider::class.java)
        }


        fun getReservations(TAG: String,id:String): MutableLiveData<List<ReservationRoom>> {

            return RoomService.database.getReservationDao().selectReservations();

        }
        private fun getReservationsWorker(){
            val saveRequest =
                PeriodicWorkRequestBuilder<SaveImageToFileWorker>(1, TimeUnit.HOURS)
                    // Additional configuration
                    .build()

        }
    }
}