package com.sil1.autolibdz_rental.data.model

import java.util.*

data class Reservation(val idReservation:Int,val etat:String,val nomBorneDepart:String,val numChassisVehicule:Int,
val numImmatriculationVehicule:Int,val modeleVehicule:String,val marqueVehicule:String,val nomBorneDestination:String, val dateReservation:Date,
val dure:Int,val distance:Int) {
}