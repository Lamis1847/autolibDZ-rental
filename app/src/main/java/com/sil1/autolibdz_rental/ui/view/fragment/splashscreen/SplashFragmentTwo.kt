package com.sil1.autolibdz_rental.ui.view.fragment.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.ui.view.activity.HomeActivity
import com.sil1.autolibdz_rental.ui.view.activity.LoginActivity
import kotlinx.android.synthetic.main.fragment_splash_one.*
import kotlinx.android.synthetic.main.fragment_splash_two.*

class SplashFragmentTwo : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_two, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        imageViewRound3_S2.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_splashFragmentTwo_to_splashFragmentThree)
        }

        imageViewRound1_S2.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_splashFragmentTwo_to_splashFragmentOne)
        }

        textViewPasserTwo.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.onBackPressed()
        }
    }
}