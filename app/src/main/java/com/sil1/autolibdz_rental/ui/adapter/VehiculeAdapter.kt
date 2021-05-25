package com.sil1.autolibdz_rental.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.ui.viewmodel.Vehicule

class VehiculesAdapter(val context: Context, var data: List<Vehicule>, var vm: Vehicule): RecyclerView.Adapter<MyViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.vehicule_layout, parent, false))

    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nomVehicule.text = data[position].marque + data[position].modele
        holder.nbPlaces.text = data[position].nbPlaces.toString()
        //holder.imageVehicule.setImageResource(data[position].imageVehicule)

        holder.detailsButton.setOnClickListener{
            vm.marque = data[position].marque
            vm.modele =  data[position].modele
            vm.nbPlaces = data[position].nbPlaces
          //vm.imageVehicule = data[position].imageVehicule
            vm.vitesse_max = data[position].vitesse_max
            vm.type_energie = data[position].type_energie
            vm.puissance_fiscale = data[position].puissance_fiscale
            vm.matricule =data[position].matricule


            holder.detailsButton.findNavController().navigate(R.id.action_listeVehiculeFragment_to_DetailsVehiculeFragment)

        }

    }

}

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val nomVehicule = view.findViewById<TextView>(R.id.nomVehicule) as TextView
    val nbPlaces = view.findViewById<TextView>(R.id.nbPlaces) as TextView
    val imageVehicule = view.findViewById<ImageView>(R.id.imageVehicule) as ImageView
    val detailsButton = view.findViewById(R.id.detailsButton) as Button
    val reserverButton = view.findViewById(R.id.reserverButton) as Button
}