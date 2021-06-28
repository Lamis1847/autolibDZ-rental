package com.sil1.autolibdz_rental.data.model

import androidx.room.PrimaryKey
import java.util.*

data class Trajet(
    @PrimaryKey
    val idTrajet:Int,
    val idReservation:Int,
    val dateDebut:String,
    val dateFin:String?,
    val tempsEstime:String?,
    val kmParcourue:Int?,
    val prixAPayer:Double?
) {
}