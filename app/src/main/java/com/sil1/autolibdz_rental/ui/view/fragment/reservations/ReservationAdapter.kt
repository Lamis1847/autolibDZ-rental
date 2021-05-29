package com.sil1.autolibdz_rental.ui.view.fragment.reservations

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.data.model.Reservation

class ReservationAdapter(val context: Context): RecyclerView.Adapter<MyViewHolder>() {
    private var data = mutableListOf<Reservation>()
    fun setReservations(reservations: List<Reservation>) {
        data.addAll(reservations)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.reservation_element, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.borneDepart.text=data[position].nomBorneDepart.toString()
        holder.borneDestination.text=data[position].nomBorneDestination.toString()
        holder.dateReservation.text=data[position].dateReservation.toString()
        holder.nomVehicule.text= data[position].marqueVehicule.toString() + data[position].modeleVehicule.toString()
        holder.etat.text=data[position].etat.toString()

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