package com.sil1.autolibdz_rental.utils

import android.view.View
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.ViewPager
import com.sil1.autolibdz_rental.ui.adapter.TransactionCardAdapter
import com.sil1.autolibdz_rental.ui.view.fragment.transactions.TransactionViewModel

class ShadowTransformer(var mViewPager: ViewPager, var mAdapter: TransactionCardAdapter, var mViewModel: TransactionViewModel): ViewPager.OnPageChangeListener, ViewPager.PageTransformer {

    //  private lateinit var mViewPager: ViewPager
    //  private lateinit var mAdapter: CardAdapter
    private  var mLastOffset = 0f
    private var mScalingEnabled = false
    init {
        mViewPager.addOnPageChangeListener(this)

    }
//    fun ShadowTransformer(viewPager: ViewPager, adapter: TransactionCardAdapter, viewModel: ViewModel) {
//         mViewPager = viewPager
//         viewPager.addOnPageChangeListener(this)
//         mAdapter = adapter
//     }

    fun enableScaling(enable: Boolean) {
        if (mScalingEnabled && !enable) { // shrink main card
            val currentCard: CardView? = mAdapter.getCardViewAt(mViewPager.getCurrentItem())
            if (currentCard != null) {
                currentCard.animate().scaleY(1F)
                currentCard.animate().scaleX(1F)
            }
        } else if (!mScalingEnabled && enable) { // grow main card
            val currentCard: CardView? = mAdapter.getCardViewAt(mViewPager.getCurrentItem())
            if (currentCard != null) {
                currentCard.animate().scaleY(1.1f)
                currentCard.animate().scaleX(1.1f)
            }
        }
        mScalingEnabled = enable
    }


    override  fun onPageScrolled(
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int
    ) {
        val realCurrentPosition: Int
        val nextPosition: Int
        val baseElevation: Float = mAdapter.baseElevation
        val realOffset: Float
        val goingLeft = mLastOffset > positionOffset
        // If we're going backwards, onPageScrolled receives the last position
// instead of the current one
        if (goingLeft) {
            realCurrentPosition = position + 1
            nextPosition = position
            realOffset = 1 - positionOffset
        } else {
            nextPosition = position + 1
            realCurrentPosition = position
            realOffset = positionOffset
        }
        // Avoid crash on overscroll
        if (nextPosition > mAdapter.number- 1
            || realCurrentPosition > mAdapter.number - 1
        ) {
            return
        }
        val currentCard: CardView? = mAdapter.getCardViewAt(realCurrentPosition)
        // This might be null if a fragment is being used
// and the views weren't created yet
        if (currentCard != null) {
            if (mScalingEnabled) {
                currentCard.setScaleX((1 + 0.1 * (1 - realOffset)).toFloat())
                currentCard.setScaleY((1 + 0.1 * (1 - realOffset)).toFloat())
            }
            currentCard.setCardElevation(
                baseElevation + (baseElevation
                        * (TransactionCardAdapter.MAX_ELEVATION_FACTOR - 1) * (1 - realOffset))
            )
        }
        val nextCard: CardView? = mAdapter.getCardViewAt(nextPosition)
        // We might be scrolling fast enough so that the next (or previous) card
// was already destroyed or a fragment might not have been created yet
        if (nextCard != null) {
            if (mScalingEnabled) {
                nextCard.scaleX = (1 + 0.1 * realOffset).toFloat()
                nextCard.scaleY = (1 + 0.1 * realOffset).toFloat()
            }
            nextCard.cardElevation = baseElevation + (baseElevation
                    * (TransactionCardAdapter.MAX_ELEVATION_FACTOR - 1) * realOffset)
        }
        mLastOffset = positionOffset
    }

    override fun onPageSelected(position: Int) {
        if (position == 0) {
            mViewModel.getAbonnementTransactions()
        }
        else {
            mViewModel.getStripeTransactions()
        }
    }

    override fun onPageScrollStateChanged(state: Int) {}
    override fun transformPage(page: View, position: Float) {
    }
}