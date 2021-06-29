package com.sil1.autolibdz_rental.data.api

import com.sil1.autolibdz_rental.data.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface ServiceProvider {
    // Getting all bornes of our system
    @GET("api/bornes/all")
    fun getAllBornes(@Header("authorization") token:String): Call<List<Borne>>

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
    fun getLocataire(@Path("id") id:String? /*@Header("Authorization") token: String*/): Call<LocataireRetro>

    @PUT("api/locataire/email/{id}")
    fun editMailLocataire(@Path("id") id:String?,@Body locataire:LocataireEditEmail/*@Header("Authorization") token: String*/): Call<LocataireModificationResponse>

    @PUT("api/locataire/password/{id}")
    fun editPasswordLocataire(@Path("id") id:String?,@Body locataire:LocataireEditPassword/*@Header("Authorization") token: String*/): Call<LocataireModificationResponse>

    //récupérer les reservation d'un locataire
    @GET("api/reservation/historique/locataires/{id}")
    fun getReservations(@Path("id") id:String /*@Header("Authorization") token: String*/): Call<List<Reservation>>

    @GET("/api/bornes/{id}/vehicules")
    fun getListeVehicules(@Path("id") id:String,@Header("authorization") token:String  ): Call<List<VehiculeModel>>


    @GET("/api/identites/locataire/{id}")
    fun getIdentiteLocataire(@Path("id") id:String,/*@Header("authorization") token:String  */): Call<Identite>

    @POST("api/reservation")
    fun ajouterReservation(@Body reservation: ReservationModel,@Header("authorization") token:String): Call<ReservationResponse>

    @POST("api/reservation")
    fun ajouterReservation(@Body reservation: ReservationModel): Call<ReservationResponse>


    @POST("api/identites")
    fun envoyerValidationDemande(@Body validationBody: ValidationBody): Call<Any>
    //create payment intent
    @FormUrlEncoded
    @POST("api/payment/create-payment-intent")
    fun createPaymentIntent(@Field("prix") prix: Int): Call<PaymentIntent>

    //make payment with subscription card
    @GET("api/abonnement/{id}")
    fun getUserBalance(@Header("Authorization") token:String, @Path("id") id: Int): Call<Balance>

    //make payment with abonnement card
    @FormUrlEncoded
    @POST("api/abonnement/{id}")
    fun payWithAbonnement(@Header("Authorization") token:String, @Path("id") id: Int , @Field("prix") prix: Double): Call<ResponseBody>

    //create transaction
    @POST("api/transaction")
    fun createTransaction(@Header("Authorization") token:String, @Body transaction: Transaction): Call<ResponseBody>

    //get user's stripe transactions
    @GET("api/transaction/{id}")
    fun getUserTransactions(@Path("id") id: Int, @Header("Authorization") token:String ): Call<ArrayList<Transaction>>

    //get user's stripe transactions
    @GET("api/trajet/getTrajetByReservation/{id}")
    fun getTrajetByReservation(@Path("id") idReservation: Int): Call<Trajet>
}