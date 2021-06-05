package com.sil1.autolibdz_rental.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.data.model.Reservation
import com.sil1.autolibdz_rental.ui.view.fragment.profil.ReservationViewModel
import com.sil1.autolibdz_rental.ui.view.fragment.reservations.ReservationAdapter
import com.sil1.autolibdz_rental.utils.reservations
import com.sil1.autolibdz_rental.utils.reservationsFiltred
import kotlinx.android.synthetic.main.activity_reservations.*
import kotlinx.android.synthetic.main.fragment_reservations.*
import kotlinx.android.synthetic.main.reservation_element.view.*
import java.util.*
import kotlin.collections.ArrayList

class ReservationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservations)
        textView3.setBackgroundResource(R.color.white)
        tout.isSelected = true;


en_cours.setOnClickListener{
reservationsFiltred.value= reservations.value?.filter { it.etat.equals("en cours",true) }
    en_cours.isSelected = true;
    annulées.isSelected = false;
    terminées.isSelected = false;
    tout.isSelected = false;

}

        annulées.setOnClickListener {
            reservationsFiltred.value = reservations.value?.filter {
                it.etat.equals("annulée", true)
            }
            en_cours.isSelected = false;
            annulées.isSelected = true;
            terminées.isSelected = false;
            tout.isSelected = false;
        }
            terminées.setOnClickListener {
            reservationsFiltred.value= reservations.value?.filter { it.etat.equals("terminée",true)
            }
                en_cours.isSelected = false;
                annulées.isSelected = false;
                terminées.isSelected = true;
                tout.isSelected = false;
        }
            tout.setOnClickListener {
                reservationsFiltred.value= reservations.value
                en_cours.isSelected = false;
                annulées.isSelected = false;
                terminées.isSelected = false;
                tout.isSelected = true;
                }

            }


        }
