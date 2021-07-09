package com.sil1.autolibdz_rental.ui.view.fragment.payment_end

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.sil1.autolibdz_rental.R
import kotlinx.android.synthetic.main.fragment_payment_end.*
import androidx.navigation.fragment.findNavController

class PaymentEndFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_end, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        paymentDoneBtn.setOnClickListener {
            findNavController().navigate(R.id.action_paymentEndFragment_to_nav_home)
        }
    }

}