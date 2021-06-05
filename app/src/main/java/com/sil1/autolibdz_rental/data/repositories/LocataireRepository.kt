package com.sil1.autolibdz_rental.data.repositories

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.sil1.autolibdz_rental.data.api.ServiceBuilder
import com.sil1.autolibdz_rental.data.api.ServiceProvider
import com.sil1.autolibdz_rental.data.model.LocataireModificationResponse
import com.sil1.autolibdz_rental.data.model.LocataireRetro
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocataireRepository {

    companion object {

        val api: ServiceProvider by lazy {
            ServiceBuilder.buildService(ServiceProvider::class.java)
        }

fun editLocataire(TAG:String,id:String,locataire:LocataireRetro):Boolean
{
var call = api.editLocataire(id,locataire) // fonction de modification dans l'api
var updateRespond:LocataireModificationResponse
var boolean=true

call.enqueue(object:Callback<LocataireModificationResponse>{
    override fun onResponse(
        call: Call<LocataireModificationResponse>,
        response: Response<LocataireModificationResponse>
    ) {
        if (!response.isSuccessful) {
            boolean=false
            Log.i(TAG, "CODE:" + response.code().toString())
        } else {
            Log.i(TAG, "REPONSES: updated successfully")
    }}

    override fun onFailure(call: Call<LocataireModificationResponse>, t: Throwable) {
        boolean=false
        Log.i(TAG, "error CODE:" + t.message)

    }

})
return boolean
}
        fun getLocataire(TAG: String,id:String): MutableLiveData<ArrayList<LocataireRetro?>> {
            var call = api.getLocataire(id) // consommation de l'api
            var locataireRespond: LocataireRetro?
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

    }

}


