package com.sil1.autolibdz_rental.ui.view.fragment.reservation_vehicule

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.ui.viewmodel.Vehicule
import kotlinx.android.synthetic.main.fragment_details_vehicule.*
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
        code = codePin.toString()

        return inflater.inflate(R.layout.fragment_vehicule_reserve2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Toast.makeText(requireActivity(), "Disabled Back Press", Toast.LENGTH_SHORT).show()
            }
        })
        val vm = ViewModelProvider(requireActivity()).get(Vehicule::class.java)
        Glide.with(requireActivity()).load(vm.secureUrl).into(imageVehicule1)
        codePINTextView1.text = code

        deverrouillerButton1.setOnClickListener {
            verrouillerButton1?.isEnabled = true
            deverrouillerButton1?.isEnabled = false
        }
        verrouillerButton1.setOnClickListener {
            deverrouillerButton1?.isEnabled = true
            verrouillerButton1?.isEnabled = false
        }
        assistanceButton1.setOnClickListener{
            val uri = Uri.parse("tel: 0550 40 56 89")
            val intent = Intent(Intent.ACTION_DIAL, uri)
            requireActivity().startActivity(intent)
        }
    }

}