package com.sil1.autolibdz_rental.data.api

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sil1.autolibdz_rental.data.model.Reservation
import com.sil1.autolibdz_rental.data.model.ReservationRoom
import com.sil1.autolibdz_rental.data.repositories.ReservationRepository.Companion.api
import com.sil1.autolibdz_rental.data.room.RoomService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyWorker(
    val ctx: Context, val paramters:
    WorkerParameters
) : Worker(ctx, paramters) {
        override fun doWork(): Result {
            var result:Boolean=false
            val id = "1"
            var call = api.getReservations(id) // consommation de l'api
            var reservationRespond: List<Reservation>?
            var reservationList = mutableListOf<Reservation>()
            var finalList = MutableLiveData<List<Reservation>>()

            call.enqueue(object : Callback<List<Reservation>> {
                override fun onResponse(
                    call: Call<List<Reservation>>,
                    response: Response<List<Reservation>>
                ) {
                    Log.i("reservation", "Display reservation List: call enqueue")

                    if (!response.isSuccessful) {
                        Log.i("reservation", "CODE:" + response.code().toString())
                        result=false
                        return
                    }
                    result = true
                    reservationRespond = response.body()  // Getting the list
                    if (reservationRespond != null) {
                        Log.i("reservation", "REPONSES: HERE is ALL THE reservations:")
                        for (m in reservationRespond!!) {
                            var content = ""
                            content += " $m \n"
                            Log.i("reservation", "\n=========\n$content")
                            reservationList.add(m)
                        }
                        finalList.value = reservationList
                        //Log.i("TAG",marqueList.toString())
                        var reservation: Reservation
                        for (reservation in reservationList) {
                            var reservation_room = ReservationRoom(
                                reservation.idReservation,
                                reservation.nomBorneDepart,
                                reservation.nomBorneDestination,
                                reservation.marqueVehicule + " " + reservation.modeleVehicule,
                                reservation.dateReservation,
                                reservation.etat
                            )
                            RoomService.database.getReservationDao()
                                .addReservation(reservation_room)

                        }
                    }
                }

                override fun onFailure(call: Call<List<Reservation>>, t: Throwable) {
                    Log.i("reservation", "error CODE:" + t.message)
                    result=true
                }
            })
        if (result) return  Result.success()
            else return Result.retry()
        }

    }
