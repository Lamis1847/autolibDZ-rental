package com.sil1.autolibdz_rental.data.model

import androidx.lifecycle.MutableLiveData
import androidx.room.*
//l'interface pour manipuler la table de docteur
@Dao
interface ReservationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addReservation(reservation:Reservation)
    @Update
    fun updateReservation(reservation:Reservation)
    @Delete
    fun deleteReservation(reservation:Reservation)
    @Query("SELECT * FROM Reservations")
    fun selectReservations():List<Reservation>
    @Query("DELETE FROM Reservations")
    fun deleteReservations()

}



