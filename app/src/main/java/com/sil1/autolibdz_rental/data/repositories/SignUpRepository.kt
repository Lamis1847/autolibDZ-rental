package com.sil1.autolibdz_rental.data.repositories

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.gson.Gson
import com.sil1.autolibdz_rental.data.api.ServiceBuilder
import com.sil1.autolibdz_rental.data.api.ServiceProvider
import com.sil1.autolibdz_rental.data.model.Locataire
import com.sil1.autolibdz_rental.data.model.SignUpGoogleBody
import com.sil1.autolibdz_rental.data.model.SignUpResponse
import com.sil1.autolibdz_rental.ui.view.activity.HomeActivity
import com.sil1.autolibdz_rental.ui.view.activity.LoginActivity
import com.sil1.autolibdz_rental.utils.idTokenUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpRepository {


    companion object {

        val api: ServiceProvider by lazy {
            ServiceBuilder.buildService(ServiceProvider::class.java)
        }

        fun createLocataire(
            context: Context,
            locataire: Locataire
        ) {

            val addLocataireRequest = api.ajouterLocataire(locataire) // consommation de l'api

            addLocataireRequest.enqueue(object : Callback<SignUpResponse> {
                override fun onResponse(
                    call: Call<SignUpResponse>,
                    response: Response<SignUpResponse>
                ) {
                    if (!response.isSuccessful()) {
                        val gson = Gson()
                        val message: SignUpResponse = gson.fromJson(
                            response.errorBody()!!.charStream(),
                            SignUpResponse::class.java
                        )
                        Toast.makeText(
                            context,
                            message.message,
                            Toast.LENGTH_LONG
                        ).show();
                        val myIntent = Intent(context, LoginActivity::class.java)
                        context.startActivity(myIntent)
                    } else {
                        val resp = response.body()
                        Toast.makeText(
                            context,
                            resp?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                    Toast.makeText(
                        context,
                        "Un erreur cest produit",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        }

        fun createLocataireGoogle(
            context: Context,
            signUpGoogleBody: SignUpGoogleBody
        ) {

            val addLocataireGoogleRequest =
                api.ajouterLocataireGoogle(signUpGoogleBody) // consommation de l'api

            addLocataireGoogleRequest.enqueue(object : Callback<SignUpResponse> {
                override fun onResponse(
                    call: Call<SignUpResponse>,
                    response: Response<SignUpResponse>
                ) {
                    //verify if is a bad request
                    if (!response.isSuccessful()) {
                        val gson = Gson()
                        val message: SignUpResponse = gson.fromJson(
                            response.errorBody()!!.charStream(),
                            SignUpResponse::class.java
                        )
                        Toast.makeText(context, message.message, Toast.LENGTH_LONG).show();


                    } else {
                        val resp = response.body()
                        Toast.makeText(context, resp?.message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                    Toast.makeText(context, "Un erreur c'est produit", Toast.LENGTH_LONG).show()
                }
            })

        }
    }
}