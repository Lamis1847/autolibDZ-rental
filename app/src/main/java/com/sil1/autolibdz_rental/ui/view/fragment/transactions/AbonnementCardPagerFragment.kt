package com.sil1.autolibdz_rental.ui.view.fragment.transactions

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaopiz.kprogresshud.KProgressHUD
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.data.model.Transaction
import com.sil1.autolibdz_rental.ui.adapter.TransactionCardAdapter
import com.sil1.autolibdz_rental.ui.adapter.TransactionRecyclerAdapter
import kotlinx.android.synthetic.main.abonnement_payment_fragment.*
import kotlinx.android.synthetic.main.activity_transaction.*
import kotlinx.android.synthetic.main.fragment_pager_card_abonnement.*

class AbonnementCardPagerFragment: TransactionCardFragment() {
    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_pager_card_abonnement, container, false)
        mCardView = view.findViewById<View>(R.id.cardView) as CardView
        mCardView!!.maxCardElevation = (mCardView!!.cardElevation
                * TransactionCardAdapter.MAX_ELEVATION_FACTOR)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        viewModel.getUserBalance(requireContext())

        viewModel.balance.observe(requireActivity(), Observer {
            balanceUserText.text = String.format("%.2f", it.userBalance)  + " DZD"
        })
    }
}