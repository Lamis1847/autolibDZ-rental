package com.sil1.autolibdz_rental.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.ui.view.fragment.reservations.ReservationAdapter
import kotlinx.android.synthetic.main.activity_reservations.*
import kotlinx.android.synthetic.main.fragment_reservations.*

class ReservationsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservations)

        textView3.setBackgroundResource(R.color.black)
       var adapter= ReservationAdapter(this)
        recycleview.layoutManager= LinearLayoutManager(this)
        recycleview.adapter = adapter
    }
}