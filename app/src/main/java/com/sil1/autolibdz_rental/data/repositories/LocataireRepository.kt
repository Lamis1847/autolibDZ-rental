package com.sil1.autolibdz_rental.data.repositories

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.sil1.autolibdz_rental.data.api.ServiceBuilder
import com.sil1.autolibdz_rental.data.api.ServiceProvider
import com.sil1.autolibdz_rental.data.model.Identite
import com.sil1.autolibdz_rental.data.model.LocataireEditEmail
import com.sil1.autolibdz_rental.data.model.LocataireEditPassword
import com.sil1.autolibdz_rental.data.model.LocataireModificationResponse
import com.sil1.autolibdz_rental.data.model.LocataireRetro
import com.sil1.autolibdz_rental.utils.userToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocataireRepository {

    companion object {

        val api: ServiceProvider by lazy {
            ServiceBuilder.buildService(ServiceProvider::class.java)
        }
        fun editPasswordLocataire(TAG:String,id:String?,token:String?,locataire:LocataireEditPassword,context:Context)
        {
            var call = api.editPasswordLocataire(id,locataire,"Basic $token") // fonction de modification dans l'api


            call.enqueue(object:Callback<LocataireModificationResponse>{
                override fun onResponse(
                    call: Call<LocataireModificationResponse>,
                    response: Response<LocataireModificationResponse>
                ) {
                    if (!response.isSuccessful) {
                        Toast.makeText(context,"Erreur de mise à jour", Toast.LENGTH_LONG).show()
                        Log.i(TAG, "CODE:" + response.code().toString())

                    } else {
                        var message = response.body().toString().split("(")
                        val test = message[1].split("=")
                        val message2 = test[1]
                        Toast.makeText(context,message2, Toast.LENGTH_LONG).show()
                    }}

                override fun onFailure(call: Call<LocataireModificationResponse>, t: Throwable) {
                    Toast.makeText(context,"Erreur de mise à jour", Toast.LENGTH_LONG).show()
                    Log.i(TAG, "error CODE:" + t.message)

                }

            })
        }



        fun editMailLocataire(TAG:String?,id:String?,token:String?,locataire:LocataireEditEmail,context:Context)
        {
            var call = api.editMailLocataire(id,locataire,"Basic $token",) // fonction de modification dans l'api

            call.enqueue(object:Callback<LocataireModificationResponse>{
                override fun onResponse(
                    call: Call<LocataireModificationResponse>,
                    response: Response<LocataireModificationResponse>
                ) {
                    if (!response.isSuccessful) {
                        Toast.makeText(context,"Erreur de mise à jour", Toast.LENGTH_LONG).show()
                        Log.i(TAG, "CODE:" + response.code().toString())
                    } else {
                        var message = response.body().toString().split("(")
                        val test = message[1].split("=")
                        var message2 = test[1]
                        message2=message2.dropLast(1)
                        Toast.makeText(context,message2, Toast.LENGTH_LONG).show()
                        Log.i(TAG, "REPONSES: updated successfully")
                    }}

                override fun onFailure(call: Call<LocataireModificationResponse>, t: Throwable) {
                    Toast.makeText(context,"Erreur de mise à jour", Toast.LENGTH_LONG).show()
                    Log.i(TAG, "error CODE:" + t.message)

                }

            })

        }

        fun getLocataire(TAG: String?,id:String?,token:String?): MutableLiveData<ArrayList<LocataireRetro?>> {
            var call = api.getLocataire(id,"Basic $token") // consommation de l'api
            var locataireRespond: LocataireRetro?
            Log.i("repositorytoken",token.toString())
            var locatairelist = ArrayList<LocataireRetro?>()
            var finalList = MutableLiveData<ArrayList<LocataireRetro?>>()
            call.enqueue(object : Callback<LocataireRetro> {
                override fun onResponse(
                    call: Call<LocataireRetro>,
                    response: Response<LocataireRetro>
                ) {

                    Log.i(TAG, "Display locataire List: call enqueue")

                    if (!response.isSuccessful) {
                        Log.i(TAG, "CODE:" + response.code().toString())
                        return
                    }

                    locataireRespond = response.body()  // Getting the list
                    if (locataireRespond != null) {
                        Log.i(TAG, "REPONSES: HERE is locataire:")
                        locatairelist.add(locataireRespond)
                    }
                    //borneList.sortBy { it.name }
                    finalList.value = locatairelist
                    //Log.i("TAG",marqueList.toString())
                }


                override fun onFailure(call: Call<LocataireRetro>, t: Throwable) {

                    Log.i(TAG, "error CODE:" + t.message)
                }
            })
            return finalList
        }


        fun getIdentiteLocataire(TAG: String, token: String, id: String, onResult: (Identite?) -> Unit){
            var call = api.getIdentiteLocataire(id) //"Basic $token",

            call.enqueue(object : Callback<Identite> {
                override fun onResponse(call: Call<Identite>, response: Response<Identite>) {
                    Log.i(TAG, "Getting user identite")

                    if (!response.isSuccessful) {
                        Log.i(TAG, "CODE:" + response.code().toString() + " Message: " + response.message())
                        onResult(null)
                    }
                    val identite = response.body()  // Getting the balance
                    identite?.toString()?.let { Log.i(TAG, it) }
                    onResult(identite)
                }


                override fun onFailure(call: Call<Identite>, t: Throwable) {
                    Log.i( TAG, "error CODE:" + t.message)
                    onResult(null)
                }
            })
        }


    }

}


