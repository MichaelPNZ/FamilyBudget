package com.example.familybudget.ui.ui.homeScreen

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.familybudget.ui.data.STUB
import com.example.familybudget.ui.model.MandatoryPayment
import com.example.familybudget.ui.model.Wallet

class HomeScreenViewModel(application: Application): AndroidViewModel(application) {

    private val appContext = application
    private val _homeScreenUIState = MutableLiveData<HomeScreenUIState>()
    val homeScreenUIState: LiveData<HomeScreenUIState>
        get() = _homeScreenUIState

    fun loadWallet(walletId: Int) {
        val wallet = STUB.getWallet(walletId)
        val balance = STUB.getBalance(walletId)

        Log.i("!!!", "wallet: $wallet")
        Log.i("!!!", "wallet: $balance")
        Log.i("!!!", "currentMonth: ${wallet?.currentMonth}")
        _homeScreenUIState.value = HomeScreenUIState(
            wallet = wallet,
            balance = balance
        )
    }

    fun onMandatoryPaymentsClicked(amount: String, selectedIcon: CharSequence) {
        val currentHomeScreenUIState = _homeScreenUIState.value ?: return
        val wallet = currentHomeScreenUIState.wallet ?: return
        val lastId = wallet.mandatoryPayments.last().id
        val mandatoryPayments = wallet.mandatoryPayments.plus(
            MandatoryPayment(
                lastId + 1,
                "$selectedIcon.png",
                selectedIcon.toString(),
                amount.toInt()
            )
        )
        val updateList = wallet.copy(mandatoryPayments = mandatoryPayments)
        _homeScreenUIState.value = HomeScreenUIState(
            wallet = updateList
        )
    }

    data class HomeScreenUIState(
        var wallet: Wallet? = null,
        val balance: String? = null,
    )

}