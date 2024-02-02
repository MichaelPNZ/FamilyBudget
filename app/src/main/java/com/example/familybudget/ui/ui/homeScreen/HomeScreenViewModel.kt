package com.example.familybudget.ui.ui.homeScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.familybudget.ui.data.STUB
import com.example.familybudget.ui.model.Wallet

class HomeScreenViewModel(application: Application): AndroidViewModel(application) {

    private val appContext = application
    private val _homeScreenUIState = MutableLiveData<HomeScreenUIState>()
    val homeScreenUIState: LiveData<HomeScreenUIState>
        get() = _homeScreenUIState

    fun loadWallet(walletId: Int) {
        val wallet = STUB.getWallet(walletId)
        val balance = STUB.getBalance(walletId)

        _homeScreenUIState.value = HomeScreenUIState(
            wallet = wallet,
            balance = balance
        )
    }

    data class HomeScreenUIState(
        val wallet: Wallet? = null,
        val balance: String? = null,
    )

}