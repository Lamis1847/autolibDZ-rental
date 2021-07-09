package com.sil1.autolibdz_rental.ui.view.fragment.abonnement_payment

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.kaopiz.kprogresshud.KProgressHUD
import com.sil1.autolibdz_rental.R
import kotlinx.android.synthetic.main.abonnement_payment_fragment.*
import kotlinx.android.synthetic.main.infos_trajet_fragment.*

class AbonnementPaymentFragment : Fragment() {
    private lateinit var viewModel: AbonnementPaymentViewModel
    private lateinit var hud: KProgressHUD
    var idReservation = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.abonnement_payment_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AbonnementPaymentViewModel::class.java)

        viewModel.prixAPayer = arguments?.getDouble("prixAPayer")!! //force as non null
        idReservation = arguments?.getInt("idReservation")!!

        viewModel.getUserBalance(requireContext())

        aPayerText.text = "-" + String.format("%.2f", viewModel.prixAPayer)  + " DZD"

        viewModel.balance.observe(requireActivity(), Observer {
            balanceText.text = String.format("%.2f", it.userBalance)  + " DZD"
            newBalance.text = String.format("%.2f",  it.userBalance.minus(viewModel.prixAPayer))  + " DZD"


            if (it.userBalance.minus(viewModel.prixAPayer) < 0) {
                payAbonnementButton.isEnabled = false
            }
            else {
                payAbonnementButton.isEnabled = false
            }
        })

        payAbonnementButton.setOnClickListener{
            val dialog = MaterialDialog(requireActivity())
                .title(R.string.procedePayment)
                .message(R.string.procedePaymentText)
                .positiveButton(R.string.checkout) { dialog ->
                    dialog.dismiss()
                    viewModel.payWithAbonnement(requireContext(), idReservation)

                    hud = KProgressHUD.create(requireActivity())
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel("Patientez s'il vous plait")
                        .setDetailsLabel("Paiement en cours")
                    hud.show()
                    viewModel.response.observe(requireActivity(), Observer {
                        if (viewModel.response.value?.message() == "OK") {
                            val handler = Handler()
                            handler.postDelayed(Runnable { hud.dismiss() }, 500)
                            var bundle = bundleOf("prixAPayer" to viewModel.prixAPayer)
                            findNavController().navigate(R.id.action_abonnementPaymentFragment_to_factureAbonnementFragment22, bundle)
                        }
                    })
                }
                .negativeButton(R.string.annuler)  { dialog ->
                    dialog.dismiss()
                }
            dialog.show()
        }

        backToTrajetInfos.setOnClickListener{

        }
    }

}