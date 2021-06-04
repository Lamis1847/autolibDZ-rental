package com.sil1.autolibdz_rental.ui.view.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.ui.adapter.VehiculesAdapter
import com.sil1.autolibdz_rental.ui.view.fragment.ListeVehiculeViewModel
import com.sil1.autolibdz_rental.ui.viewmodel.Reservation
import com.sil1.autolibdz_rental.ui.viewmodel.Vehicule
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.fragment_liste_vehicule.*


class ListeVehiculeFragment : Fragment() {
    private lateinit var viewModel: ListeVehiculeViewModel
    private var myDrawerController: MyDrawerController? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        myDrawerController = try {
            activity as MyDrawerController
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement MyDrawerController")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val resViewModel = ViewModelProvider(requireActivity()).get(Reservation::class.java)

        Toast.makeText(requireActivity(),"Yo je suis la borne : ${resViewModel.idBorneDestination}",Toast.LENGTH_SHORT).show()
        Log.i("ListVehicule","yo je suis la borne ${resViewModel.idBorneDestination}")
        viewModel = ViewModelProvider(requireActivity()).get(ListeVehiculeViewModel::class.java)

        viewModel.getListeVehicule(resViewModel.idBorneDepart.toString())
        myDrawerController?.setDrawer_Locked();

        return inflater.inflate(R.layout.fragment_liste_vehicule, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        myDrawerController?.setDrawer_UnLocked()
    }

    override  fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerViewVehicule.layoutManager = LinearLayoutManager(requireActivity())


        viewModel.vehicules.observe(viewLifecycleOwner, Observer {
            checkList()
        })
        goBackButtonListeVehicule.setOnClickListener{ this.findNavController().navigate(R.id.action_ListeVehiculeFragment_pop)}

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