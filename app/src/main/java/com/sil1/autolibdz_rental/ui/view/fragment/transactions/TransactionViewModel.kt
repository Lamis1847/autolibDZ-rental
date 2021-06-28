package com.sil1.autolibdz_rental.ui.view.fragment.transactions

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sil1.autolibdz_rental.data.model.Balance
import com.sil1.autolibdz_rental.data.model.Transaction
import com.sil1.autolibdz_rental.data.repositories.AbonnementRepository
import com.sil1.autolibdz_rental.data.repositories.TransactionRepository
import com.sil1.autolibdz_rental.utils.sharedPrefFile

class TransactionViewModel: ViewModel() {
    private val TAG = "TAG-TransactionViewModel"
    private val _transactionList = MutableLiveData<ArrayList<Transaction>>()
    private var _allTransactionList:  ArrayList<Transaction>? = ArrayList<Transaction>()
    val transactionList: LiveData<ArrayList<Transaction>>
        get() = _transactionList

    private val _balance = MutableLiveData<Balance>()
    val balance: LiveData<Balance>
        get() = _balance

    fun updateTransactionList(list: ArrayList<Transaction>) = _transactionList.postValue(list)

    fun getUserTransactions(context: Context) {
        val sharedPref = context.getSharedPreferences(
            sharedPrefFile, Context.MODE_PRIVATE
        )

        val token = sharedPref.getString("token", "default")!!
        //val id = sharedPref.getString("userID", "1")!!.toInt()
        val id = 1

        TransactionRepository.getUserTransactions(TAG, token, id) {
            Log.i(TAG, "view model here")
            _allTransactionList= it
            getAbonnementTransactions()
        }
    }

    fun getStripeTransactions(){
        if (_allTransactionList!= null){
            _transactionList.postValue(
                _allTransactionList!!.filter { transaction ->transaction.modePaiement== "Stripe"  } as ArrayList<Transaction>
            )
        }
    }

    fun getAbonnementTransactions(){
        if (_allTransactionList!= null){
            _transactionList.postValue(
                _allTransactionList!!.filter { transaction ->transaction.modePaiement== "Paiement Carte d\'abonnement" || transaction.modePaiement== "Rechargement"  } as ArrayList<Transaction>
            )
        }
    }

    fun getUserBalance(context: Context) {
        val sharedPref = context.getSharedPreferences(
            sharedPrefFile, Context.MODE_PRIVATE
        )

        val token = sharedPref.getString("token", "default")!!
        //val id = sharedPref.getString("userID", "1")!!.toInt()
        val id = 1

        AbonnementRepository.getUserBalance(TAG, token, id) {
            Log.i(TAG, "view model here")
            _balance.postValue(it)
        }
    }

}