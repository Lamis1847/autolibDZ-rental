package com.sil1.autolibdz_rental.ui.view.fragment.stripe_card

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.afollestad.date.month
import com.afollestad.date.year
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import com.kaopiz.kprogresshud.KProgressHUD
import com.sil1.autolibdz_rental.R
import com.stripe.android.Stripe
import kotlinx.android.synthetic.main.abonnement_payment_fragment.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.stripe_card_fragment.*
import java.lang.ref.WeakReference
import java.util.*

@Suppress("DEPRECATION")
class StripeCardFragment : Fragment() {
    private lateinit var viewModel: StripeCardViewModel
    //private lateinit var paymentIntentClientSecret: String
    private lateinit var stripe: Stripe
    private lateinit var hud: KProgressHUD
    protected val sharedPrefFile = "CreditCardInfo"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.stripe_card_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StripeCardViewModel::class.java)
        viewModel.prixAPayer = arguments?.getDouble("prixAPayer")!!

        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)

        //check whether to use saved cardNumber
        val sharedCardValue = sharedPreferences.getString("credit_card_number", "default")
        if (sharedCardValue!="default"){
            val dialog = MaterialDialog(requireActivity())
                .title(R.string.useSavedCreditCard)
                .message(R.string.useSavedCreditCardText)
                .positiveButton(R.string.yes) { dialog ->
                    emailInput.setText(sharedPreferences.getString("email", "default"))
                    cardNumberInput.setText(sharedCardValue)
                    ccvInput.setText(sharedPreferences.getString("ccv", "123"))
                    expirationDateInput.text = sharedPreferences.getString("exp_date", "2022/06")
                }
                .negativeButton(R.string.no)  { dialog ->
                    dialog.dismiss()
                }

            dialog.show()
        }

        stripe = Stripe(
            requireActivity().applicationContext,
            "pk_test_51IpB9FFlA46GQCJtPCuVBzXAaWbT4Nwiy6RchGcxO2OeOHNLrQXBm0TgR4LcICPZQ9cgMKqkytKN2pUU9vGjkZSw00WhixIzkx"
        )

        payButton.setOnClickListener {
            var inputValidation = true
            //input validation
            if (cardNumberInput.text?.isEmpty() == true) {
                inputValidation= false
                cardNumberInput.setHintTextColor(resources.getColor(R.color.redAlert))
            }
            else if (!cardNumberInput.isCardValid) {
                inputValidation= false
                Toast.makeText(context, "Carte non valide", Toast.LENGTH_SHORT).show()
            }
            if (emailInput.text.isEmpty()) {
                inputValidation= false
                emailInput.setHintTextColor(resources.getColor(R.color.redAlert))
            }
            if (ccvInput.text.isEmpty()) {
                inputValidation= false
                ccvInput.setHintTextColor(resources.getColor(R.color.redAlert))
            }
            if (inputValidation) {
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString("email", emailInput.text.toString())
                editor.putString("credit_card_number", cardNumberInput.text.toString())
                editor.putString("ccv", ccvInput.text.toString())
                editor.putString("exp_date", expirationDateInput.text.toString())
                editor.apply()
                editor.commit()

                startCheckout()
            }
        }

        expirationDateInput.setOnClickListener {
             showCalendar()
        }

        viewModel.cardExpirationDate.observe(requireActivity(), Observer {
            if (viewModel.cardExpirationDate.value != null)
                expirationDateInput.text =
                    viewModel.cardExpirationDate.value!!.year.toString() + "/" + (viewModel.cardExpirationDate.value!!.month.plus(
                        1
                    )).toString()
        })
    }

    private fun showCalendar() {
        var datePicked : Calendar?= null
        MaterialDialog(requireActivity()).show {
            datePicker(requireFutureDate = true, minDate = Calendar.getInstance()) { dialog, date ->
                // Use date (Calendar)
                datePicked = date
            }
            positiveButton {
                viewModel.updateExpirationDate(datePicked)
            }
            negativeButton {}
        }
    }

    private fun startCheckout() {
        val dialog = MaterialDialog(requireActivity())
            .title(R.string.procedePayment)
            .message(R.string.procedePaymentText)
            .positiveButton(R.string.checkout) { dialog ->
                //create payment intent
                viewModel.createPaymentIntent(viewModel.prixAPayer!!.times(100).toInt(), requireContext())
                hud = KProgressHUD.create(requireActivity())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Patientez s'il vous plait")
                    .setDetailsLabel("Paiement en cours")
                hud.show()
                viewModel.paymentIntent.observe(requireActivity(), Observer {
                    if (viewModel.paymentIntent.value?.message != null) {
                        val handler = Handler()
                        handler.postDelayed(Runnable { hud.dismiss() }, 500)
                        var bundle = bundleOf("prixAPayer" to viewModel.prixAPayer)
                        requireActivity().findNavController(R.id.payment_test).navigate(
                            R.id.action_stripeCardFragment2_to_factureCarteFragment,
                            bundle
                        )
                    }
                })

            }
            .negativeButton(R.string.annuler)  { dialog ->
                dialog.dismiss()
            }

        dialog.show()

    }
}