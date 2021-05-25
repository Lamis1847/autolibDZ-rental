package com.sil1.autolibdz_rental.ui.view.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.ui.adapter.VehiculesAdapter
import com.sil1.autolibdz_rental.ui.view.fragment.ListeVehiculeViewModel
import com.sil1.autolibdz_rental.ui.viewmodel.Vehicule
import kotlinx.android.synthetic.main.fragment_liste_vehicule.*

class ListeVehiculeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_liste_vehicule, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerViewVehicule.layoutManager = LinearLayoutManager(requireActivity())
        val viewmodel = ViewModelProvider(this).get(ListeVehiculeViewModel::class.java)
        val data = viewmodel.vehicules.value



        val toast = Toast.makeText(context, "Hello Javatpoint ${viewmodel.vehicules.value}", Toast.LENGTH_SHORT)
        toast.show()


        val vm = ViewModelProvider(this).get(Vehicule::class.java)

        if(data != null){
            recyclerViewVehicule.adapter = VehiculesAdapter(requireActivity() ,data,vm)
        }

    }


 /*   private fun loadData(d: ArrayList<Vehicule>?):List<Vehicule> {
        var data = ArrayList<Vehicule>()
        data = d
     /*   val v1 = Vehicule("")
        v1.marque = "Peugeot"
        v1.modele = "208"
        v1.nbPlaces=  5
        v1.imageVehicule = R.drawable.vehiculeimg
        v1.capacite_max = "blabla"
        v1.vitesse_max = "150 KM/H"
        v1.puissance_fiscale = "blabla"
        v1.type_energie = "blabla"

        val v2 = Vehicule()
        v2.marque = "Peugeot"
        v2.modele = "208"
        v2.nbPlaces=  5
        v2.imageVehicule = R.drawable.vehiculeimg
        v2.capacite_max = "blabla"
        v2.vitesse_max = "150 KM/H"
        v2.puissance_fiscale = "blabla"
        v2.type_energie = "blabla"

        val v3 = Vehicule()
        v3.marque = "Peugeot"
        v3.modele = "208"
        v3.nbPlaces=  5
        v3.imageVehicule = R.drawable.vehiculeimg
        v3.capacite_max = "blabla"
        v3.vitesse_max = "150 KM/H"
        v3.puissance_fiscale = "blabla"
        v3.type_energie = "blabla"

        val v4 = Vehicule()
        v4.marque = "Peugeot"
        v4.modele = "208"
        v4.nbPlaces=  5
        v4.imageVehicule = R.drawable.vehiculeimg
        v4.capacite_max = "blabla"
        v4.vitesse_max = "150 KM/H"
        v4.puissance_fiscale = "blabla"
        v4.type_energie = "blabla"

        val v5 = Vehicule()
        v5.marque = "Peugeot"
        v5.modele = "208"
        v5.nbPlaces=  5
        v5.imageVehicule = R.drawable.vehiculeimg
        v5.capacite_max = "blabla"
        v5.vitesse_max = "150 KM/H"
        v5.puissance_fiscale = "blabla"
        v5.type_energie = "blabla"

        val v6 = Vehicule()
        v6.marque = "Peugeot"
        v6.modele = "208"
        v6.nbPlaces=  5
        v6.imageVehicule = R.drawable.vehiculeimg
        v6.capacite_max = "blabla"
        v6.vitesse_max = "150 KM/H"
        v6.puissance_fiscale = "blabla"
        v6.type_energie = "blabla"


        data.add(v1)
        data.add(v2)
        data.add(v3)
        data.add(v4)
        data.add(v5)
        data.add(v6)*/



        return data
    }*/
}