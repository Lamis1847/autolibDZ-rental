package com.sil1.autolibdz_rental.ui.view.fragment.reservation_vehicule

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.data.model.ReclamationModel
import com.sil1.autolibdz_rental.data.model.ReclamationResponse
import com.sil1.autolibdz_rental.data.model.ReservationModel
import com.sil1.autolibdz_rental.data.repositories.ReclamationRepository
import com.sil1.autolibdz_rental.data.repositories.SignUpRepository
import com.sil1.autolibdz_rental.ui.viewmodel.Reservation
import com.sil1.autolibdz_rental.ui.viewmodel.Vehicule
import com.sil1.autolibdz_rental.utils.sharedPrefFile
import kotlinx.android.synthetic.main.fragment_infos_reservation.*
import kotlinx.android.synthetic.main.fragment_reclamation.*

class ReclamationFragment : Fragment() {
    private lateinit var viewModel: ReclamationViewModel

    private lateinit var userID: String
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(ReclamationViewModel::class.java)
        return inflater.inflate(R.layout.fragment_reclamation, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val preferences = requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        userID = preferences.getString("userID", "Default").toString()
        token = preferences.getString("token", "Default").toString()

        val adapter = ArrayAdapter.createFromResource(requireActivity(), R.array.reclamation_array, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = adapter
        envoyerRecButton.setOnClickListener {
            getValues()
        }
    }

    fun getValues() {
        Toast.makeText(requireActivity(), "Votre réclamation a été bien envoyé", Toast.LENGTH_LONG).show()
        var reclamation = ReclamationModel(
            descriptionTextR.text.toString(),
            spinner2.selectedItem.toString()
        )
        viewModel.ajouterReclamation(reclamation, userID  )
        viewModel.reclamation.observe(viewLifecycleOwner, Observer {
            val data = viewModel.reclamation.value
        })
    }
}
