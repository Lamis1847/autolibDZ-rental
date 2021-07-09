package com.sil1.autolibdz_rental.ui.view.fragment.profil

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.data.model.LocataireRetro
import kotlinx.android.synthetic.main.fragment_profil.*
import java.util.ArrayList
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.sil1.autolibdz_rental.data.model.LocataireEditEmail
import com.sil1.autolibdz_rental.ui.view.activity.MyDrawerController
import com.sil1.autolibdz_rental.utils.sharedPrefFile
import com.sil1.autolibdz_rental.utils.userToken



class ProfilFragment : Fragment() {
//    // TODO: Rename and change types of parameters


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
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_profil, container, false)
        myDrawerController?.setDrawer_UnLocked();
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        // myDrawerController?.setDrawer_Locked()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val preferences = requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        val token = preferences.getString("token", "defaultvalue")
        val userID = preferences.getString("userID", "defaultvalue")
        Log.i("iddd",userID.toString())
        Log.i("Fragment",token.toString())

        var viewModel = ViewModelProvider(this).get(ProfilViewModel::class.java)
        viewModel.getLocataire(token,userID)
        viewModel.locataire.observe(requireActivity(), Observer {
                locataire ->
            updateLocataire(locataire)
        })
        SauvgarderP.setOnClickListener{

            val locataire=LocataireEditEmail(email.text.toString(),password.text.toString())
            viewModel.updateMailLocataire(locataire,token,userID,requireActivity()) //il faut changer l'id après!
            password.text.clear()
            viewModel.getLocataire(token,userID)

        }

        changerMdp.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_nav_profil_to_motDePasseFragment)

        }
        DeconnecterP.setOnClickListener{

        }
    }
    private fun updateLocataire(locataire: ArrayList<LocataireRetro?>)
    {

        nom.setText(locataire.get(0)?.nom)
        prenom.setText(locataire.get(0)?.prenom)
        email.setText(locataire.get(0)?.email)

    }


}