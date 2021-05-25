package com.sil1.autolibdz_rental.ui.viewmodel
import androidx.lifecycle.ViewModel
import com.google.gson.annotations.SerializedName

class Vehicule:ViewModel(){
    var id=0
    var marque=""
    var modele=""
    var matricule=""
    var nbPlaces=0
    var imageVehicule=0
    var type_energie=""
    var vitesse_max=""
    var capacite_max=""
    var puissance_fiscale=""
}
  /*  @SerializedName("id")
    var id: Int,
    @SerializedName("marque")
    var marque: String,
    @SerializedName("modele")
    var modele:String,
    @SerializedName("matricule")
    var matricule:String,
    @SerializedName("nbPlaces")
    var nbPlaces:Int,
    @SerializedName("imageVehicule")
    var imageVehicule:Int,
    @SerializedName("type_energie")
    var type_energie:String,
    @SerializedName("vitesse_max")
    var vitesse_max:String,
    @SerializedName("capacite_max")
    var capacite_max:String,
    @SerializedName("puissance_fiscale")
    var puissance_fiscale:String,
)*/



