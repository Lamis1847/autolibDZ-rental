package com.sil1.autolibdz_rental.ui.view.fragment.profil

import android.app.Activity
import android.content.Context
import android.os.Bundle
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
import com.sil1.autolibdz_rental.ui.view.activity.MyDrawerController
import com.sil1.autolibdz_rental.utils.sharedPrefFile


//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"
//
///**
// * A simple [Fragment] subclass.
// * Use the [ProfilFragment.newInstance] factory method to
// * create an instance of this fragment.
// */
class ProfilFragment : Fragment() {
//    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
    var list= mutableListOf<String>()
    var list2= mutableListOf<String>()
    var boolean:Boolean=false
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
        myDrawerController?.setDrawer_UnLocked();
        return inflater.inflate(R.layout.fragment_profil, container, false)


    }
    override fun onDestroyView() {
        super.onDestroyView()
        myDrawerController?.setDrawer_Locked()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val preferences = this.activity?.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val userID = preferences?.getString("userID", "Default")
        var viewModel = ViewModelProvider(this).get(ProfilViewModel::class.java)
       viewModel.getLocataire(userID)
        viewModel.locataire.observe(requireActivity(), Observer {
                locataire ->
            updateLocataire(locataire)
        })
        button.setOnClickListener{
            list2.add(nom.text.toString())
            list2.add(prenom.text.toString())
            list2.add(email.text.toString())
            if(!(list2.isEmpty()))
            if(!list.equals(list2))
            {
                val locataire=LocataireRetro(null,nom.text.toString(),prenom.text.toString(),email.text.toString(),"test123456",null)
               boolean= viewModel.updateLocataire(locataire,"193") //il faut changer l'id après!
            //    Log.i("testTAG", "Display locataire List: call enqueue")
                if(boolean)
                Toast.makeText(requireActivity(), "mise à jour avec succès", Toast.LENGTH_LONG).show()
                else
                Toast.makeText(requireActivity(), "erreur de mise à jour", Toast.LENGTH_LONG).show()
            }

            nom.text.clear()
            prenom.text.clear()
            email.text.clear()
            new_password.text.clear()

        }

    }
    private fun updateLocataire(locataire: ArrayList<LocataireRetro?>)
    {

            nom.setText(locataire.get(0)?.nom)
            prenom.setText(locataire.get(0)?.prenom)
            email.setText(locataire.get(0)?.email)
        list.add(locataire.get(0)?.nom.toString())
        list.add(locataire.get(0)?.prenom.toString())
        list.add(locataire.get(0)?.email.toString())

    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment ProfilFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            ProfilFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}