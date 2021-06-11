package com.sil1.autolibdz_rental.ui.view.fragment.facture_carte

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sil1.autolibdz_rental.R
import kotlinx.android.synthetic.main.facture_abonnement_fragment.*
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

        viewModel.prixAPayer = arguments?.getDouble("prixAPayer")!!
        payedFactureText.text = String.format("%.2f", viewModel.prixAPayer)  + " DZD"

        /*date and time of transaction*/
        var currentDateTime= LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("dd-MM HH:mm")
        val formatted = currentDateTime.format(formatter)

        factureDateText.text = formatted
    }

}