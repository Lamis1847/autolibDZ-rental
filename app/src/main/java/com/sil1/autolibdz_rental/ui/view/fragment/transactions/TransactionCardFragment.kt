package com.sil1.autolibdz_rental.ui.view.fragment.transactions

import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

abstract class TransactionCardFragment : Fragment() {
    var mCardView: CardView? = null

    val cardView: CardView?
        get() = mCardView
}
