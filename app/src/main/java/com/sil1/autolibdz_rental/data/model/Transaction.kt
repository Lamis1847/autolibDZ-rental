package com.sil1.autolibdz_rental.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Transaction(
    @SerializedName("idLocataire")
    var idLocataire:Int,
    @SerializedName("idReservation")
    var idReservation:Int,
    @SerializedName("dateTransaction")
    var dateTransaction:String,
    @SerializedName("montant")
    var montant:Double,
    @SerializedName("modePaiement")
    var modePaiement:String
)