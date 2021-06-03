package com.sil1.autolibdz_rental.data.model

import com.google.gson.annotations.SerializedName

data class VehiculeModel(
    @SerializedName("numChassis")
    var numChassis:Int,
    @SerializedName("numImmatriculation")
    var numImmatriculation:Int,
    @SerializedName("modele")
    var modele:String,
    @SerializedName("marque")
    var marque:String,
    @SerializedName("couleur")
    var couleur:String,
    @SerializedName("etat")
    var etat:String,
    @SerializedName("tempsDeRefroidissement")
    var tempsDeRefroidissement:Int,
    @SerializedName("pressionHuileMoteur")
    var pressionHuileMoteur:Int,
    @SerializedName("chargeBatterie")
    var chargeBatterie:Int,
    @SerializedName("anomalieCircuit")
    var anomalieCircuit:String,
    @SerializedName("pressionPneus")
    var pressionPneus:Int,
    @SerializedName("niveauMinimumHuile")
    var niveauMinimumHuile:Int,
    @SerializedName("regulateurVitesse")
    var regulateurVitesse:Int,
    @SerializedName("limiteurVitesse")
    var limiteurVitesse:Int,
    @SerializedName("idAgentMaintenance")
    var idAgentMaintenance:Int,
    @SerializedName("idBorne")
    var idBorne:Int,
    @SerializedName("idCloudinary")
    var idCloudinary:String,
    @SerializedName("secureUrl")
    var secureUrl:String,
)