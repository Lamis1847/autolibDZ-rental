package com.sil1.autolibdz_rental.data.model

import androidx.room.*
//l'interface pour manipuler la table de docteur
@Dao
interface ReservationDao {
    @Insert
    fun addReservation(reservation:ReservationRoom)
    @Update
    fun updateReservation(reservation:ReservationRoom)
    @Delete
    fun deleteReservation(reservation:ReservationRoom)
    @Query("SELECT * FROM Reservations")
    fun selectReservations():List<ReservationRoom>

}



