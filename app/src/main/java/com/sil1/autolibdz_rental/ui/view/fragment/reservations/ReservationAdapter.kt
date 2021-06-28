package com.sil1.autolibdz_rental.ui.view.fragment.reservations

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.data.model.Reservation
import com.sil1.autolibdz_rental.utils.idReservation
import com.sil1.autolibdz_rental.utils.reservationsFiltred
import java.text.DateFormat
import java.text.SimpleDateFormat

class ReservationAdapter(val context: Context): RecyclerView.Adapter<MyViewHolder>() {
        private var data = mutableListOf<Reservation>()
        fun setReservations(reservations: List<Reservation>) {
            data.clear()
            data.addAll(reservations)
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.reservation_element, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.borneDepart.text=data[position].nomBorneDepart
        holder.borneDestination.text=data[position].nomBorneDestination
        val formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        var today:String? = "-"
        if(data[position].dateReservation!=null) {
                today = formatter.format(data[position].dateReservation)
            }
        holder.dateReservation.text = today
        holder.nomVehicule.text= data[position].marqueVehicule + " "+ data[position].modeleVehicule
        holder.etat.text=data[position].etat.toString()
        when(holder.etat.text.toString()) {
           "En cours" -> holder.etat.setBackgroundResource(R.color.palette_yellow)
            "Terminée"->holder.etat.setBackgroundResource(R.color.terminée)
            "Annulée" -> holder.etat.setBackgroundResource(R.color.annulée)
        }
        holder.detail.setOnClickListener {view ->
            idReservation=data[position].idReservation
            if(reservationsFiltred!=null)
            view?.findNavController()?.navigate(R.id.action_nav_history_to_detailReservationFragment32)

        }
    }

    override fun getItemCount()=data.size
}





class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val borneDepart = view.findViewById<TextView>(R.id.borneDepart)
    val borneDestination = view.findViewById<TextView>(R.id.borneDestination)
    val nomVehicule = view.findViewById<TextView>(R.id.nomVehicule)
    val dateReservation = view.findViewById<TextView>(R.id.dateReservation)
    val etat = view.findViewById<TextView>(R.id.etat)
    val detail = view.findViewById<Button>(R.id.detail)
}