package com.sil1.autolibdz_rental.data.repositories

import android.content.Context
import android.widget.Toast
import com.auth0.android.jwt.JWT
import com.google.gson.Gson
import com.sil1.autolibdz_rental.data.api.ServiceBuilder
import com.sil1.autolibdz_rental.data.api.ServiceProvider
import com.sil1.autolibdz_rental.data.model.LoginUser
import com.sil1.autolibdz_rental.data.model.SignInBody
import com.sil1.autolibdz_rental.utils.userToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogInRepository {

    companion object {

        val api: ServiceProvider by lazy {
            ServiceBuilder.buildService(ServiceProvider::class.java)
        }


        fun login(
            context: Context,
            email: String,
            password: String
        ) {
            var signinbody = SignInBody(email, password)
            val authLocataireRequest = api.userLogin(signinbody) // consommation de l'api

            authLocataireRequest.enqueue(object : Callback<LoginUser> {

                override fun onResponse(call: Call<LoginUser>, response: Response<LoginUser>) {
                    if (!response.isSuccessful()) {
                        val gson = Gson()
                        val message: LoginUser = gson.fromJson(
                            response.errorBody()!!.charStream(),
                            LoginUser::class.java
                        )
                        Toast.makeText(context, "Erreur dans le login", Toast.LENGTH_LONG).show();

                    } else {
                        val resp = response.body()
                        Toast.makeText(context, "Connexion établie", Toast.LENGTH_SHORT).show()

                        userToken = resp?.token.toString()

                        var jwt = JWT(userToken)
                        var claimID = jwt.getClaim("id") //claimID to have the connected user's ID
                        var claimRole = jwt.getClaim("role")

                        /*//saving the information from the token
                        val sharedPreference =  getSharedPreferences("com.example.autoLibDZ", Context.MODE_PRIVATE)
                        var editor = sharedPreference.edit()
                        editor.putString("userID", claimID.asString())
                        editor.putString("userRole",claimRole.asString())
                        editor.putBoolean("connected",true)
                        editor.commit()*/

                        //val intent = Intent(context,ProfilActivity::class.java)
                        //startActivity(intent)
                        //finish()*/
                    }
                }

                override fun onFailure(call: Call<LoginUser>, t: Throwable) {
                    Toast.makeText(context, "Erreur", Toast.LENGTH_SHORT).show()
                }
            })

        }
    }
}