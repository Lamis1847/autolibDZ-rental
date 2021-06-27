package com.sil1.autolibdz_rental.ui.view.fragment.facture_carte

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.sil1.autolibdz_rental.R
import kotlinx.android.synthetic.main.facture_abonnement_fragment.*
import kotlinx.android.synthetic.main.facture_abonnement_fragment.factureDateText
import kotlinx.android.synthetic.main.facture_abonnement_fragment.payedFactureText
import kotlinx.android.synthetic.main.facture_abonnement_fragment.skipBtn
import kotlinx.android.synthetic.main.facture_carte_fragment.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class FactureCarteFragment : Fragment() {

    companion object {
        fun newInstance() = FactureCarteFragment()
    }

    private lateinit var viewModel: FactureCarteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.facture_carte_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FactureCarteViewModel::class.java)

        activity?.onBackPressedDispatcher?.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {}
        })

        viewModel.prixAPayer = arguments?.getDouble("prixAPayer")!!
        payedFactureText.text = String.format("%.2f", viewModel.prixAPayer)  + " DZD"

        /*date and time of transaction*/
        var currentDateTime= LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("dd-MM HH:mm")
        val formatted = currentDateTime.format(formatter)

        factureDateText.text = formatted

        skipBtn.setOnClickListener{
            requireActivity().findNavController(R.id.payment_test).navigate(R.id.action_factureCarteFragment_to_paymentEndFragment)
        }
    }

    fun sendEmail(view: View) {
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("CreditCardInfo",
            Context.MODE_PRIVATE)

        //check whether to use saved cardNumber
        val recipient = sharedPreferences.getString("credit_card_number", "default").toString().trim()
        val subject = "Votre facture AutolibDZ"
        val message = "Merci d'avoir utilisé autolib"
    }

}