package com.sil1.autolibdz_rental.data.model

import com.google.gson.annotations.SerializedName

data class Borne(
    @SerializedName("pk")
    var idBorne:Int,
    @SerializedName("nomBorne")
    var nomBorne:String,
    @SerializedName("wilaya")
    var wilaya:String,
    @SerializedName("commune")
    var commune:String,
    @SerializedName("latitude")
    var latitude:Float,
    @SerializedName("longitude")
    var longitude:Float,
    @SerializedName("nbVehicules")
    var nbVehicules:Int,
    @SerializedName("nbPlaces")
    var nbPlaces:Int,
)