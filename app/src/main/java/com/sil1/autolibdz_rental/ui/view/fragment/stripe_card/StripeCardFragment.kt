package com.sil1.autolibdz_rental.ui.view.fragment.stripe_card

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.afollestad.date.dayOfMonth
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import com.google.gson.GsonBuilder
import com.sil1.autolibdz_rental.R
import com.stripe.android.Stripe
import com.stripe.android.getPaymentIntentResult
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.StripeIntent
import com.stripe.android.view.CardInputWidget
import kotlinx.android.synthetic.main.abonnement_payment_fragment.*
import kotlinx.android.synthetic.main.stripe_card_fragment.*
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.lang.ref.WeakReference

@Suppress("DEPRECATION")
class StripeCardFragment : Fragment() {
    private lateinit var viewModel: StripeCardViewModel
    //private lateinit var paymentIntentClientSecret: String
    private lateinit var stripe: Stripe

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.stripe_card_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StripeCardViewModel::class.java)
        viewModel.prixAPayer = arguments?.getDouble("prixAPayer")

        stripe = Stripe(requireActivity().applicationContext, "pk_test_51IpB9FFlA46GQCJtPCuVBzXAaWbT4Nwiy6RchGcxO2OeOHNLrQXBm0TgR4LcICPZQ9cgMKqkytKN2pUU9vGjkZSw00WhixIzkx")

        payButton.setOnClickListener { startCheckout() }
    }

    private fun displayAlert(
        activity: Activity,
        title: String
    ) {
        requireActivity().runOnUiThread{
            val builder = AlertDialog.Builder(activity)
                .setTitle(title)

            builder.setPositiveButton("Ok", null)
            builder.create().show()
        }
    }

    private fun startCheckout() {
        val dialog = MaterialDialog(requireActivity())
            .title(R.string.procedePayment)
            .message(R.string.procedePaymentText)
            .positiveButton(R.string.checkout) { dialog ->
                val weakActivity = WeakReference<Activity>(requireActivity())
                //create payment intent
                viewModel.createPaymentIntent(viewModel.prixAPayer!!.times(100).toInt())

                viewModel.paymentIntent.observe(requireActivity(), Observer {
//                    weakActivity.get()?.let { activity ->
//                        displayAlert(
//                            activity,
//                            "Payment succeeded"
//                        )
//                    }
                })
                var bundle = bundleOf("prixAPayer" to viewModel.prixAPayer)
                requireActivity().findNavController(R.id.payment_test).navigate(R.id.action_stripeCardFragment2_to_factureCarteFragment, bundle)
            }
            .negativeButton(R.string.annuler)  { dialog ->
                dialog.dismiss()
            }


        dialog.show()

    }
}