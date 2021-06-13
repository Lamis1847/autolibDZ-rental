package com.sil1.autolibdz_rental.ui.view.fragment.reservation_vehicule

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sil1.autolibdz_rental.R
import kotlinx.android.synthetic.main.fragment_vehicule_reserve2.*

class VehiculeReserve2Fragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    var code=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var codePin= arguments?.get("codePin")
        Log.i("HELLOOOOOOOOOOOOOOOO",codePin.toString())
        code = codePin.toString()
        return inflater.inflate(R.layout.fragment_vehicule_reserve2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        codePINTextView1.text = code
    }

}