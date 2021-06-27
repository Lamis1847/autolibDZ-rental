package com.sil1.autolibdz_rental.data.api

import com.google.gson.annotations.SerializedName
import com.sil1.autolibdz_rental.data.model.*
import okhttp3.ResponseBody
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

    //create payment intent
    @FormUrlEncoded
    @POST("api/payment/create-payment-intent")
    fun createPaymentIntent(@Field("prix") prix: Int): Call<PaymentIntent>

    //make payment with subscription card
    @GET("api/abonnement/{id}")
    fun getUserBalance(@Path("id") id: Int): Call<Balance>

    //make payment with abonnement card
    @FormUrlEncoded
    @POST("api/abonnement/{id}")
    fun payWithAbonnement(@Path("id") id: Int , @Field("prix") prix: Double): Call<ResponseBody>

    //create transaction
    @POST("api/transaction")
    fun createTransaction(@Body transaction: Transaction): Call<ResponseBody>
}