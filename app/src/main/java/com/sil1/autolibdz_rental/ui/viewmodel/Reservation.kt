package com.sil1.autolibdz_rental.ui.viewmodel

import androidx.lifecycle.ViewModel

class Reservation: ViewModel() {
    var idBorneDepart= 0
    var idBorneDestination= 0
    var nomBorneDepart = ""
    var nomBorneDestination=""
    var tempsEstimeEnSecondes= 0.0
    var tempsEstimeHumanReadable = ""
    var distanceEstime = 0L
}