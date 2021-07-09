package com.sil1.autolibdz_rental.ui.view.fragment.reservation_vehicule

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.HandlerCompat.postDelayed
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sil1.autolibdz_rental.R
import kotlin.math.absoluteValue


class VehiculeReserveFragment : Fragment() {
    private lateinit var rootview:View
    private lateinit var bundle:Bundle
    private val TAG = "_vehiculeResFragment"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var codePin = arguments?.get("codePin")
        var id = arguments?.getInt("id")!!
        bundle = bundleOf("codePin" to codePin, "id" to id)

        activity?.onBackPressedDispatcher?.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Toast.makeText(requireActivity(), "Disabled Back Press", Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //splash()
        rootview = inflater.inflate(R.layout.fragment_vehicule_reserve, container, false)
        findNavController().navigate(R.id.action_vehiculeReserveFragment_to_vehiculeReserve2Fragment,bundle)
        return rootview
    }

//    fun splash(){
//        Thread(Runnable {
//            Thread.sleep(500)
//            findNavController().navigate(R.id.action_vehiculeReserveFragment_to_vehiculeReserve2Fragment,bundle)
//        }).start()
//    }
}