package com.sil1.autolibdz_rental.ui.view.fragment.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.ui.view.activity.LoginActivity
import kotlinx.android.synthetic.main.fragment_splash_one.*



class SplashFragmentOne : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_one, container, false)
    }

    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        imageViewRound2_S1.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_splashFragmentOne_to_splashFragmentTwo)
        }

        imageViewRound3_S1.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_splashFragmentOne_to_splashFragmentTwo)
        }

        textViewPasserOne.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}