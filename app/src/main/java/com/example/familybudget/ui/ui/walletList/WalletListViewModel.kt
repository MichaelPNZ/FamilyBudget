package com.example.familybudget.ui.ui.walletList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.familybudget.ui.data.STUB
import com.example.familybudget.ui.model.Wallet

class WalletListViewModel(application: Application): AndroidViewModel(application) {

    private val _walletListUIState = MutableLiveData<WalletListUIState>()
    val walletListUIState: LiveData<WalletListUIState>
        get() = _walletListUIState

    fun loadUser(walletId: Int) {
        val allWallet = STUB.getAllWallets()

        _walletListUIState.value = WalletListUIState(
            currentId = walletId,
            listWallet = allWallet
        )
    }

    data class WalletListUIState(
        val currentId: Int? = null,
        val listWallet: List<Wallet>? = null,
    )
}