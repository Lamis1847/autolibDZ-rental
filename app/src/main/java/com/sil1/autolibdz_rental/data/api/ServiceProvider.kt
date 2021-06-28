package com.sil1.autolibdz_rental.data.api

import com.sil1.autolibdz_rental.data.model.*
import retrofit2.Call
import retrofit2.http.*


interface ServiceProvider {
    // Getting all bornes of our system
    @GET("api/bornes/all")
    fun getAllBornes(/*@Header("Authorization") token: String*/): Call<List<Borne>>

    //authentification locataire
    @POST("api/auth/locataire")
    fun userLogin(
        @Body info: SignInBody
    ):Call<LoginUser>

    //add new  Locataire
    @POST("api/locataire/createLocataire")
    fun ajouterLocataire(@Body userData: Locataire): Call<SignUpResponse>

    //add new  Locataire with google api , password == mail
    @POST("api/locataire/createLocataireGmail")
    fun ajouterLocataireGoogle(@Body token: SignUpGoogleBody): Call<SignUpResponse>

    //récupérer les informations d'un locataire
    @GET("api/locataire/{id}")
    fun getLocataire(@Header("authorization") token:String?,@Path("id") id:String?): Call<LocataireRetro>

    @PUT("api/locataire/email/{id}")
    fun editMailLocataire(@Header("authorization") token:String?,@Path("id") id:String?,@Body locataire:LocataireEditEmail): Call<LocataireModificationResponse>

    @PUT("api/locataire/password/{id}")
    fun editPasswordLocataire(@Header("authorization") token:String?,@Path("id") id:String?,@Body locataire:LocataireEditPassword): Call<LocataireModificationResponse>

    //récupérer les reservation d'un locataire
    @GET("api/reservation/historique/locataires/{id}")
    fun getReservations(@Path("id") id:String /*@Header("Authorization") token: String*/): Call<List<Reservation>>



    @GET("/api/bornes/{id}/vehicules")
    fun getListeVehicules(@Path("id") id:String  ): Call<List<VehiculeModel>>

    @POST("api/reservation")
    fun ajouterReservation(@Body reservation: ReservationModel): Call<ReservationResponse>
}