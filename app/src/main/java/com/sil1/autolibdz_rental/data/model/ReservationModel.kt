package com.sil1.autolibdz_rental.data.model

data class ReservationModel(
    var etat: String,
    var idLocataire: Int,
    var idVehicule: Int,
    var idBorneDepart: Int,
    var idBorneDestination: Int,
    var tempsEstime: Int,
    var distanceEstime: Float,
    var prixEstime: Float,
    )