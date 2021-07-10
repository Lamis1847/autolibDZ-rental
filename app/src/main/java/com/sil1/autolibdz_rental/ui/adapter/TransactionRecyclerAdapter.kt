package com.sil1.autolibdz_rental.ui.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.data.model.Transaction
import com.sil1.autolibdz_rental.ui.view.fragment.transactions.TransactionViewModel
import kotlinx.android.synthetic.main.row_transaction.view.*

class TransactionRecyclerAdapter(private var list: ArrayList<Transaction>, val context: Context, val vm: TransactionViewModel) :
    RecyclerView.Adapter<TransactionRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val modePaiement: TextView = itemView.modePaiement
        val dateTransaction: TextView = itemView.dateTransaction
        val montant: TextView = itemView.montant
        val shadowTransaction: ImageView = itemView.shadowTransaction
        val solidTransaction: ImageView = itemView.solidTransaction
        val iconTransaction: ImageView = itemView.iconTransaction

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.row_transaction, null)


        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dateTransaction.text = list[position].dateTransaction?.substring(0,10)
        Log.i("tralala", list[position].dateTransaction!!)
        holder.montant.text = list[position].montant.toString()

        if (list[position].modePaiement == "Stripe") {
            holder.modePaiement.text = list[position].modePaiement
            holder.shadowTransaction.setBackgroundResource(R.drawable._shadow_gray)
            holder.solidTransaction.setBackgroundResource(R.drawable._square_gray_shadow)
            holder.iconTransaction.setBackgroundResource(R.drawable.ic_car_white)
        }
        else if (list[position].modePaiement == "Paiement Carte d'abonnement") {
            holder.modePaiement.text = "Paiement"
            holder.shadowTransaction.setBackgroundResource(R.drawable._shadow_gray)
            holder.solidTransaction.setBackgroundResource(R.drawable._square_gray_shadow)
            holder.iconTransaction.setBackgroundResource(R.drawable.ic_car_white)
        }
        else if (list[position].modePaiement == "Rechargement") {
            holder.modePaiement.text = list[position].modePaiement
            holder.shadowTransaction.setBackgroundResource(R.drawable._shadow_yellow)
            holder.solidTransaction.setBackgroundResource(R.drawable._square_yellow_shadow)
            holder.iconTransaction.setBackgroundResource(R.drawable.ic_suitcase_white)
        }

//        holder.itemView.setOnClickListener {
//            vm.fullName = list[position].fullName
//            vm.address = list[position].address
//            vm.phoneNumber = "0" + list[position].phoneNumber
//            holder.itemView.findNavController()?.navigate(R.id.action_mainFragment_to_detailsFragment)
//        }

    }

}
