package com.sil1.autolibdz_rental.ui.view.fragment.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sil1.autolibdz_rental.data.model.Transaction

class TransactionViewModel: ViewModel() {
    private val _transactionList = MutableLiveData<ArrayList<Transaction>>()
    val transactionList: LiveData<ArrayList<Transaction>>
        get() = _transactionList

    fun updateTransactionList(list: ArrayList<Transaction>) = _transactionList.postValue(list)


}