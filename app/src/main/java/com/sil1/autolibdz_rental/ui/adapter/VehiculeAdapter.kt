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
import com.sil1.autolibdz_rental.data.model.VehiculeModel
import com.sil1.autolibdz_rental.ui.viewmodel.Reservation
import com.sil1.autolibdz_rental.ui.viewmodel.Vehicule

class VehiculesAdapter(
    val context: Context,
    var data: List<VehiculeModel>,
    var vm: Vehicule,
    var vmRes: Reservation,
    var resViewModel: Reservation,
    /*idBorneDepart: Int,
    idBorneDestination: Int,
    tempsEstimeEnSecondes: Double,
    tempsEstimeHumanReadable: String,
    distanceEstime: Long,*/
): RecyclerView.Adapter<MyViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.vehicule_layout, parent, false))

    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nomVehicule.text = data[position].marque + " "+ data[position].modele
        //holder.imageVehicule.setImageResource(data[position].imageVehicule)

        holder.detailsButton.setOnClickListener{
            vm.marque = data[position].marque
            vm.modele =  data[position].modele
            vm.numChassis = data[position].numChassis
            vm.numImmatriculation = data[position].numImmatriculation
            vm.pressionHuileMoteur = data[position].pressionHuileMoteur
            vm.pressionPneus = data[position].pressionPneus
            vm.regulateurVitesse = data[position].regulateurVitesse
            vm.tempsDeRefroidissement =data[position].tempsDeRefroidissement
            vm.anomalieCircuit =data[position].anomalieCircuit
            vm.chargeBatterie =data[position].chargeBatterie
            vm.couleur =data[position].couleur
            vm.etat = data[position].etat
            vm.limiteurVitesse = vm.limiteurVitesse
           /* vmRes.idBorneDepart = resViewModel.idBorneDepart
            vmRes.idBorneDestination = resViewModel.idBorneDestination
            vmRes.tempsEstimeEnSecondes = resViewModel.tempsEstimeEnSecondes
            vmRes.tempsEstimeHumanReadable = resViewModel.tempsEstimeHumanReadable
            vmRes.distanceEstime = resViewModel.distanceEstime*/
            holder.detailsButton.findNavController().navigate(R.id.action_listeVehiculeFragment_to_detailsVehiculeFragment)

        }
        holder.reserverButton.setOnClickListener{
            vm.marque = data[position].marque
            vm.modele =  data[position].modele
            vmRes.idBorneDepart = resViewModel.idBorneDepart
            vmRes.idBorneDestination = resViewModel.idBorneDestination
            vmRes.tempsEstimeEnSecondes = resViewModel.tempsEstimeEnSecondes
            vmRes.tempsEstimeHumanReadable = resViewModel.tempsEstimeHumanReadable
            vmRes.distanceEstime = resViewModel.distanceEstime
            holder.reserverButton.findNavController().navigate(R.id.action_listeVehiculeFragment_to_infosReservationFragment)
        }



    }

}

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val nomVehicule = view.findViewById<TextView>(R.id.nomVehicule) as TextView
    val imageVehicule = view.findViewById<ImageView>(R.id.imageVehicule) as ImageView
    val detailsButton = view.findViewById(R.id.detailsButton) as Button
    val reserverButton = view.findViewById(R.id.reserverButton) as Button
}