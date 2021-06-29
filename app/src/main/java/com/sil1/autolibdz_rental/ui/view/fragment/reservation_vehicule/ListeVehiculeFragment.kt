package com.sil1.autolibdz_rental.ui.view.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.data.room.RoomService
import com.sil1.autolibdz_rental.ui.adapter.VehiculesAdapter
import com.sil1.autolibdz_rental.ui.view.fragment.ListeVehiculeViewModel
import com.sil1.autolibdz_rental.ui.view.fragment.map_display.MapDisplayViewModel
import com.sil1.autolibdz_rental.ui.view.fragment.map_display.MapDisplayViewModelFactory
import com.sil1.autolibdz_rental.ui.view.fragment.reservation_vehicule.ListeVehiculeViewModelFactory
import com.sil1.autolibdz_rental.ui.viewmodel.Reservation
import com.sil1.autolibdz_rental.ui.viewmodel.Vehicule
import com.sil1.autolibdz_rental.utils.sharedPrefFile
import kotlinx.android.synthetic.main.fragment_liste_vehicule.*


class ListeVehiculeFragment : Fragment() {
    private lateinit var viewModel: ListeVehiculeViewModel
    private var myDrawerController: MyDrawerController? = null
    lateinit var resViewModel : Reservation
    private val TAG = "_listeVehiculeFragment"
    private var token : String = ""
    private var userID : String = ""
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
        val sharedPref = RoomService.context.getSharedPreferences(
            sharedPrefFile, Context.MODE_PRIVATE
        )
        token = sharedPref.getString("token","defaultvalue").toString()
        userID = sharedPref.getString("userID","defaultvalue").toString()
        if (token != null) {
            Log.i(TAG, token)
        }
        val factory = ListeVehiculeViewModelFactory(token)
        viewModel = ViewModelProviders.of(this, factory).get(ListeVehiculeViewModel::class.java)
         resViewModel = ViewModelProvider(requireActivity()).get(Reservation::class.java)

//        viewModel = ViewModelProvider(requireActivity()).get(ListeVehiculeViewModel::class.java)

        viewModel.getListeVehicule(resViewModel.idBorneDepart.toString(),token)

        myDrawerController?.setDrawer_Locked();

        return inflater.inflate(R.layout.fragment_liste_vehicule, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //myDrawerController?.setDrawer_UnLocked()
    }

    override  fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerViewVehicule.layoutManager = LinearLayoutManager(requireActivity())

        nomBorne.text = resViewModel.nomBorneDepart
        viewModel.vehicules.observe(viewLifecycleOwner, Observer {
            checkList()
        })
        goBackButtonListeVehicule.setOnClickListener{ this.findNavController().navigate(R.id.action_ListeVehiculeFragment_pop)}
    }


    fun checkList() {
        val data = viewModel.vehicules.value
        Log.i(TAG,"distanceEstime"+resViewModel.distanceEstime.toString() )
        Log.i(TAG,"tempsEstimeEnSecondes"+resViewModel.tempsEstimeEnSecondes.toString() )
        val vm = ViewModelProvider(requireActivity()).get(Vehicule::class.java)
        val vmRes = ViewModelProvider(requireActivity()).get(Reservation::class.java)
        if(data != null){
            recyclerViewVehicule.adapter = VehiculesAdapter(requireActivity() ,data,vm,vmRes,resViewModel)
        }
    }

 }