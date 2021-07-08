package com.sil1.autolibdz_rental.ui.view.fragment.profil

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.data.model.LocataireEditEmail
import com.sil1.autolibdz_rental.data.model.LocataireEditPassword
import com.sil1.autolibdz_rental.utils.sharedPrefFile
import kotlinx.android.synthetic.main.fragment_mot_de_passe.*
import kotlinx.android.synthetic.main.fragment_profil.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MotDePasseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MotDePasseFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mot_de_passe, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val preferences = requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val token = preferences.getString("token", "default")
        val userID = preferences.getString("userID", "1")
        var viewModel = ViewModelProvider(this).get(ProfilViewModel::class.java)
        SauvgarderMdp.setOnClickListener {
            val locataire= LocataireEditPassword(oldPassword.text.toString(),newPassword.text.toString())
            viewModel.updatePasswordLocataire(locataire,token,userID,requireActivity()) //il faut changer l'id après!
            oldPassword.text.clear()
            newPassword.text.clear()

        }
    }


}