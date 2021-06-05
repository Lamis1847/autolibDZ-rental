package com.sil1.autolibdz_rental.ui.view.fragment.reservations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.data.model.Reservation
import com.sil1.autolibdz_rental.ui.view.fragment.profil.ReservationViewModel
import com.sil1.autolibdz_rental.utils.reservations
import com.sil1.autolibdz_rental.utils.reservationsFiltred
import kotlinx.android.synthetic.main.fragment_reservations.*
import java.util.*

//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"
//
///**
// * A simple [Fragment] subclass.
// * Use the [ReservationsFragment.newInstance] factory method to
// * create an instance of this fragment.
// */
class ReservationsFragment : Fragment() {
//    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
private lateinit var adapter: ReservationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservations, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        val test = Reservation(1,"test","test",12,12,"test","test","test", Date("12/02/2021"),12,12)
//        val test2 = mutableListOf<Reservation>(test)
//        adapter.setReservations(test2)
        adapter = ReservationAdapter(requireActivity())

        var viewModel = ViewModelProvider(this).get(ReservationViewModel::class.java)
        viewModel.getReservations();
        reservations.observe(requireActivity(), Observer {
                reservations ->
            adapter.setReservations(reservations)
        })

        reservationsFiltred.observe(requireActivity(),Observer {
                reservationsFiltred ->
            adapter.setReservations(reservationsFiltred)

        })
        recycleview.layoutManager= LinearLayoutManager(requireActivity())
        recycleview.adapter = adapter

    }
//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment ReservationsFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            ReservationsFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}