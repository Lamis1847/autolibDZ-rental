package com.sil1.autolibdz_rental.data.api

import com.sil1.autolibdz_rental.ui.viewmodel.Vehicule
import retrofit2.Call
import retrofit2.http.GET

interface ServiceProvider {
   /* @GET("api/vehicules/")
    fun getVehiculeDetails(id: Int.Companion): Call<Vehicule>*/
    @GET("api/vehicules/")
    fun getListeVehicules(/*@Header("Authorization") token: String*/): Call<List<Vehicule>>

}