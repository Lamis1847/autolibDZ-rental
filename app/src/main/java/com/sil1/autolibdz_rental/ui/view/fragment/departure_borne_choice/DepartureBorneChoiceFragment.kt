package com.sil1.autolibdz_rental.ui.view.fragment.departure_borne_choice

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sil1.autolibdz_rental.R

class DepartureBorneChoiceFragment : Fragment() {

    companion object {
        fun newInstance() = DepartureBorneChoiceFragment()
    }

    private lateinit var viewModel: DepartureBorneChoiceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.departure_borne_choice_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DepartureBorneChoiceViewModel::class.java)
        // TODO: Use the ViewModel
    }

}