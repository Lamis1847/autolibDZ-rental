package com.sil1.autolibdz_rental.ui.view.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaopiz.kprogresshud.KProgressHUD
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.ui.adapter.CardFragmentPagerAdapter
import com.sil1.autolibdz_rental.ui.adapter.TransactionRecyclerAdapter
import com.sil1.autolibdz_rental.ui.view.fragment.transactions.TransactionViewModel
import com.sil1.autolibdz_rental.utils.ShadowTransformer
import kotlinx.android.synthetic.main.activity_transaction.*

class TransactionActivity : AppCompatActivity() {
    private lateinit var mFragmentCardAdapter: CardFragmentPagerAdapter
    private lateinit var mFragmentCardShadowTransformer: ShadowTransformer
    private lateinit var hud: KProgressHUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        mFragmentCardAdapter = CardFragmentPagerAdapter(supportFragmentManager,
            dpToPixels(1, this))

        val vm = ViewModelProvider(this).get(TransactionViewModel::class.java)
        vm.getUserTransactions(this)

        mFragmentCardShadowTransformer = ShadowTransformer(viewPager, mFragmentCardAdapter, vm)

        viewPager.adapter = mFragmentCardAdapter

        viewPager.setPageTransformer(false, mFragmentCardShadowTransformer)

        viewPager.offscreenPageLimit = 3
        mFragmentCardShadowTransformer.enableScaling(true)


        //populating the recycler
        hud = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Patientez s'il vous plait")
            .setDetailsLabel("Chargement de vos transactions")
        hud.show()
        vm.transactionList.observe(this, Observer {
            if (vm.transactionList.value != null) {
                val handler = Handler()
                handler.postDelayed(Runnable { hud.dismiss() }, 500)
                val list = vm.transactionList.value
                val  adapter = TransactionRecyclerAdapter(it, this,vm)

                transactionCardsRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
                transactionCardsRecycler.adapter = adapter
            }
        })
    }

    private fun dpToPixels(dp: Int, context: Context): Float {
        return dp * context.resources.displayMetrics.density
    }
}