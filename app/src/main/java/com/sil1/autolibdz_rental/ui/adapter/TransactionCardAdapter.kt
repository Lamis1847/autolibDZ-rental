package com.sil1.autolibdz_rental.ui.adapter

import androidx.cardview.widget.CardView

interface TransactionCardAdapter {
    val baseElevation: Float

    fun getCardViewAt(position: Int): CardView?
    val number: Int

    companion object {
        const val MAX_ELEVATION_FACTOR = 5
    }
}