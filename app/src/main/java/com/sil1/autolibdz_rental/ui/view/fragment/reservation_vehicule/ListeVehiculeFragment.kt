package com.sil1.autolibdz_rental.ui.view.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.ui.adapter.VehiculesAdapter
import com.sil1.autolibdz_rental.ui.view.fragment.ListeVehiculeViewModel
import com.sil1.autolibdz_rental.ui.viewmodel.Vehicule
import kotlinx.android.synthetic.main.fragment_liste_vehicule.*

class ListeVehiculeFragment : Fragment() {
    private lateinit var viewModel: ListeVehiculeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(requireActivity()).get(ListeVehiculeViewModel::class.java)

        viewModel.getListeVehicule()

        return inflater.inflate(R.layout.fragment_liste_vehicule, container, false)
    }

    override  fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerViewVehicule.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.vehicules.observe(viewLifecycleOwner, Observer {
            checkList()
        })
        //requireActivity().goBackButtonListeVehicule.setOnClickListener { checkList() }
    }


    fun checkList() {
        val data = viewModel.vehicules.value
        val vm = ViewModelProvider(requireActivity()).get(Vehicule::class.java)
        if(data != null){
            recyclerViewVehicule.adapter = VehiculesAdapter(requireActivity() ,data,vm)
        }
    }

 }