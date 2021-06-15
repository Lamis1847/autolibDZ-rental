package com.sil1.autolibdz_rental.ui.view.fragment.reservations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.utils.idReservation
import com.sil1.autolibdz_rental.utils.reservations
import com.sil1.autolibdz_rental.utils.reservationsFiltred
import kotlinx.android.synthetic.main.fragment_detail_reservation.*
import kotlinx.android.synthetic.main.fragment_profil.*
import java.text.DateFormat
import java.text.SimpleDateFormat

class DetailReservationFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_reservation, container, false)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        var today:String? = "-"
        val reservation = reservations.value?.filter { it?.idReservation==idReservation }
        if(reservation?.get(0)?.dateReservation!=null) {
            today = formatter.format(reservation?.get(0)?.dateReservation)
        }
        dateR.text= today
        departR.text= reservation?.get(0)?.nomBorneDepart.toString()
        dureeR.text= reservation?.get(0)?.dure.toString() + " min"
        if(reservation?.get(0)?.distance.toString().contains("null",true))
        distanceR.text= "-"
        else
            distanceR.text= reservation?.get(0)?.distance.toString() + " km"


        destinationR.text= reservation?.get(0)?.nomBorneDestination.toString()
        vehiculeR.text= reservation?.get(0)?.marqueVehicule.toString() + " "+ reservation?.get(0)?.modeleVehicule.toString()
        etatR.text= reservation?.get(0)?.etat.toString()
        when(etatR.text.toString())
        {
            "En cours" -> etatR.setBackgroundResource(R.color.palette_yellow)
            "Terminée"-> etatR.setBackgroundResource(R.color.terminée)
            "Annulée" -> etatR.setBackgroundResource(R.color.annulée)
        }
        Glide.with(requireActivity()).load(reservation?.get(0)?.secureUrl)
            .into(vehiculeD)
        activity?.onBackPressedDispatcher?.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                view?.findNavController()?.navigate(R.id.action_detailReservationFragment3_to_nav_history)

            }
        })


    }


}