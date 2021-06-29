package com.sil1.autolibdz_rental.ui.view.fragment.transactions

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaopiz.kprogresshud.KProgressHUD
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.ui.adapter.CardFragmentPagerAdapter
import com.sil1.autolibdz_rental.ui.adapter.TransactionRecyclerAdapter
import com.sil1.autolibdz_rental.ui.view.fragment.reservation_vehicule.InfosReservationViewModel
import com.sil1.autolibdz_rental.utils.ShadowTransformer
import kotlinx.android.synthetic.main.activity_transaction.*
import kotlinx.android.synthetic.main.fragment_transaction.view.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class TransactionFragment : Fragment() {
    private lateinit var mFragmentCardAdapter: CardFragmentPagerAdapter
    private lateinit var mFragmentCardShadowTransformer: ShadowTransformer
    private lateinit var hud: KProgressHUD
    lateinit var mview:View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mview=  inflater.inflate(R.layout.fragment_transaction, container, false)
        mFragmentCardAdapter = CardFragmentPagerAdapter(
            requireActivity().supportFragmentManager,
            dpToPixels(1, requireActivity())
        )

        val vm = ViewModelProvider(requireActivity()).get(TransactionViewModel::class.java)
        vm.getUserTransactions(requireActivity())

        mFragmentCardShadowTransformer = ShadowTransformer(
            mview.viewPager,
            mFragmentCardAdapter,
            vm
        )

        mview.viewPager.adapter = mFragmentCardAdapter

        mview.viewPager.setPageTransformer(false, mFragmentCardShadowTransformer)

        mview.viewPager.offscreenPageLimit = 3
        mFragmentCardShadowTransformer.enableScaling(true)


        //populating the recycler
        hud = KProgressHUD.create(requireActivity())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Patientez s'il vous plait")
            .setDetailsLabel("Chargement de vos transactions")
        hud.show()
        vm.transactionList.observe(viewLifecycleOwner, Observer {
            if (vm.transactionList.value != null) {
                val handler = Handler()
                handler.postDelayed(Runnable { hud.dismiss() }, 500)
                val list = vm.transactionList.value
                val adapter = TransactionRecyclerAdapter(it, requireActivity(), vm)

                transactionCardsRecycler.layoutManager = LinearLayoutManager(
                    requireActivity(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
                transactionCardsRecycler.adapter = adapter
            }
        })
        return mview
    }

    private fun dpToPixels(dp: Int, context: Context): Float {
        return dp * context.resources.displayMetrics.density
    }
}