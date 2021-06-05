package com.sil1.autolibdz_rental.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Reservations")
data class ReservationRoom(
    @PrimaryKey
    var reservationId:Int,
    var borneDepart:String,
    var BorneDestination:String,
    var vehicule:String,
    var date: Date,
    var etat:String






    )
