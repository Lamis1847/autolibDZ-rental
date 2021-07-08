package com.sil1.autolibdz_rental.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Reservations")
data class Reservation(

    @PrimaryKey
    val idReservation:Int,
    val etat:String?,
    val nomBorneDepart:String?,
    val numChassisVehicule:Int?,
    val numImmatriculationVehicule:Int?,
    val modeleVehicule:String?,
    val marqueVehicule:String?,
    val nomBorneDestination:String?,
    val secureUrl:String?,
    val dateReservation:Date?,
    val dure:Int,
    val distance:Int?,
    val prix:Float?
    ) {
}