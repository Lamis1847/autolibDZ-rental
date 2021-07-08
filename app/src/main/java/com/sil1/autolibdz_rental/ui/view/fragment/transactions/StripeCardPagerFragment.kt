package com.sil1.autolibdz_rental.ui.view.fragment.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.data.model.Transaction
import com.sil1.autolibdz_rental.ui.adapter.TransactionCardAdapter
import com.sil1.autolibdz_rental.ui.adapter.TransactionRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_pager_card_abonnement.*
import kotlinx.android.synthetic.main.fragment_pager_card_stripe.*

class StripeCardPagerFragment: TransactionCardFragment() {

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_pager_card_stripe, container, false)
        mCardView = view.findViewById<View>(R.id.cardView) as CardView
        mCardView!!.maxCardElevation = (mCardView!!.cardElevation
                * TransactionCardAdapter.MAX_ELEVATION_FACTOR)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val vm = ViewModelProvider(requireActivity()).get(TransactionViewModel::class.java)

        //populating the recycler
//        val list = ArrayList<Transaction>()
//
//        list.add(
//            Transaction(1,1,90.0,
//                "Rechargement")
//        )
//        list.add(
//            Transaction(1,2,80.0,
//                "Paiement Carte d'abonnement")
//        )
//
//
//        val  adapter = TransactionRecyclerAdapter(list, requireActivity(),vm)

//        stripeRecycler.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)
//
//        stripeRecycler.adapter = adapter
    }
}