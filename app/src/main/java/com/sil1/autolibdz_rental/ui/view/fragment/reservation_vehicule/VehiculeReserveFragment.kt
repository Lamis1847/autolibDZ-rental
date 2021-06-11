package com.sil1.autolibdz_rental.ui.view.fragment.reservation_vehicule

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sil1.autolibdz_rental.R


class VehiculeReserveFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicule_reserve, container, false)
        Thread.sleep(3000);
        findNavController().navigate(R.id.action_vehiculeReserveFragment_to_vehiculeReserve2Fragment)
    }


}