package com.sil1.autolibdz_rental.ui.view.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.ui.adapter.CardFragmentPagerAdapter
import com.sil1.autolibdz_rental.utils.ShadowTransformer
import kotlinx.android.synthetic.main.activity_transaction.*

class TransactionActivity : AppCompatActivity() {
    private lateinit var mFragmentCardAdapter: CardFragmentPagerAdapter
    private lateinit var mFragmentCardShadowTransformer: ShadowTransformer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        mFragmentCardAdapter = CardFragmentPagerAdapter(supportFragmentManager,
            dpToPixels(1, this))

        mFragmentCardShadowTransformer = ShadowTransformer(viewPager, mFragmentCardAdapter)

        viewPager.adapter = mFragmentCardAdapter

        viewPager.setPageTransformer(false, mFragmentCardShadowTransformer)

        viewPager.offscreenPageLimit = 3
        mFragmentCardShadowTransformer.enableScaling(true)
    }

    private fun dpToPixels(dp: Int, context: Context): Float {
        return dp * context.resources.displayMetrics.density
    }
}