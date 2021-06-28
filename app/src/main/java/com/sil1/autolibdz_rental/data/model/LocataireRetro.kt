package com.sil1.autolibdz_rental.data.model

//import com.google.gson.annotations.SerializedName

data class LocataireRetro(
//    @SerializedName("idLocataire")
    var idLocataire:String?,
//    @SerializedName("nom")
    var nom:String?,
//    @SerializedName("prenom")
    var prenom:String?,
//    @SerializedName("email")
    var email:String?,
//    @SerializedName("motDePasse")
    var motDePasse:String?,
//    @SerializedName("Active")
    var Active:Boolean?,
    var isDeleted:Boolean?
    )

