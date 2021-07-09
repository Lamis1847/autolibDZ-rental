package com.sil1.autolibdz_rental.ui.view.fragment.facture_abonnement

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.findNavController
import com.sil1.autolibdz_rental.R
import kotlinx.android.synthetic.main.facture_abonnement_fragment.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FactureAbonnementFragment : Fragment() {

    companion object {
        fun newInstance() = FactureAbonnementFragment()
    }

    private lateinit var viewModel: FactureAbonnementViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.facture_abonnement_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FactureAbonnementViewModel::class.java)

        viewModel.prixAPayer = arguments?.getDouble("prixAPayer")!!
        payedFactureText.text = String.format("%.2f", viewModel.prixAPayer)  + " DZD"

        activity?.onBackPressedDispatcher?.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {}
        })

        /*date and time of transaction*/
        var currentDateTime= LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("dd-MM HH:mm")
        val formatted = currentDateTime.format(formatter)

        factureDateText.text = formatted

        skipBtn.setOnClickListener{
            findNavController().navigate(R.id.action_factureAbonnementFragment2_to_paymentEndFragment)
        }
    }

}