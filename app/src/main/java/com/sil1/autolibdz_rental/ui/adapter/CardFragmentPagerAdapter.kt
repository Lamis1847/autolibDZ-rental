package com.sil1.autolibdz_rental.ui.adapter

import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.sil1.autolibdz_rental.ui.view.fragment.transactions.AbonnementCardPagerFragment
import com.sil1.autolibdz_rental.ui.view.fragment.transactions.StripeCardPagerFragment
import com.sil1.autolibdz_rental.ui.view.fragment.transactions.TransactionCardFragment
import java.util.ArrayList

class CardFragmentPagerAdapter(fm: FragmentManager, baseElevation: Float) :
    FragmentStatePagerAdapter(fm), TransactionCardAdapter {
    private val mFragments: MutableList<TransactionCardFragment>
    override val baseElevation: Float

    override fun getCardViewAt(position: Int): CardView? {
        return mFragments[position].cardView
    }

    override val number: Int
        get() =mFragments.size

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {

        return mFragments.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment: Any = super.instantiateItem(container, position)
        mFragments[position] = fragment as TransactionCardFragment
        return fragment
    }

    fun addCardFragment(fragment: TransactionCardFragment) {
        mFragments.add(fragment)
    }

    init {
        mFragments = ArrayList<TransactionCardFragment>()
        this.baseElevation = baseElevation
        addCardFragment(AbonnementCardPagerFragment())
        addCardFragment(StripeCardPagerFragment())
    }
}