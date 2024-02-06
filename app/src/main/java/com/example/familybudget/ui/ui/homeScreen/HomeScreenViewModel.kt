package com.example.familybudget.ui.ui.homeScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.familybudget.ui.data.WalletsRepository
import com.example.familybudget.ui.model.MandatoryPayment
import com.example.familybudget.ui.model.Wallet
import kotlinx.coroutines.launch

class HomeScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val appContext = application
    private val walletsRepository = WalletsRepository(appContext)
    private val _homeScreenUIState = MutableLiveData<HomeScreenUIState>()
    val homeScreenUIState: LiveData<HomeScreenUIState>
        get() = _homeScreenUIState

    fun loadWallet(walletId: Int) {
        viewModelScope.launch {
            val allWalletCache = walletsRepository.getWallets()
            val walletCache = walletsRepository.getWalletById(walletId)
            if (allWalletCache == null || walletCache == null) {
                val wallet = Wallet(currentMonth = walletsRepository.getCurrentMonth())
                val balance = (wallet.monthlyIncome - wallet.monthlyExpenses).toString()
                _homeScreenUIState.postValue(HomeScreenUIState(
                    wallet = wallet,
                    balance = balance
                ))
                walletsRepository.insertWallet(wallet)
            } else {
                val balance = (walletCache.monthlyIncome - walletCache.monthlyExpenses).toString()
                _homeScreenUIState.postValue(HomeScreenUIState(
                    wallet = walletCache,
                    balance = balance
                ))
            }
        }
    }

    fun onMandatoryPaymentsClicked(amount: String, selectedIcon: CharSequence) {
        val category = when (selectedIcon) {
            "Банк" -> "bank"
            "Подписки" -> "dollar"
            "Спорт" -> "gym"
            "Дом" -> "home"
            "Детский сад" -> "kindergarten"
            "Телефон" -> "phone"
            "Школа" -> "shcool"
            "Магазин" -> "store"
            else -> {
                "error"
            }
        }
        val currentHomeScreenUIState = _homeScreenUIState.value ?: return
        val wallet = currentHomeScreenUIState.wallet ?: return
        val lastId = wallet.mandatoryPayments.lastOrNull()?.id ?: -1
        if (lastId >= 0) {
            updateUI(
                wallet,
                lastId + 1,
                category,
                selectedIcon.toString(),
                amount.toInt()
            )
        } else {
            updateUI(
                wallet,
                0,
                category,
                selectedIcon.toString(),
                amount.toInt()
            )
        }
    }

    fun deleteMandatoryPayments(mandatoryPaymentsId: Int) {
        val wallet = _homeScreenUIState.value?.wallet ?: return
        val mandatoryPaymentToDelete = wallet.mandatoryPayments.find {
            it.id == mandatoryPaymentsId
        } ?: return
        val updateList = wallet.copy(
            mandatoryPayments = wallet.mandatoryPayments.minus(mandatoryPaymentToDelete)
        )
        viewModelScope.launch {
            walletsRepository.deleteWallet(updateList)
            _homeScreenUIState.postValue(HomeScreenUIState(wallet = updateList))
        }
    }

    private fun updateUI(wallet: Wallet, id: Int, imageUrl: String, name: String, amount: Int) {
        val mandatoryPayments = wallet.mandatoryPayments.plus(
            MandatoryPayment(
                id,
                "$imageUrl.png",
                name,
                amount
            )
        )
        val updateList = wallet.copy(mandatoryPayments = mandatoryPayments)
        _homeScreenUIState.value = HomeScreenUIState(
            wallet = updateList
        )
        viewModelScope.launch {
            walletsRepository.insertWallet(updateList)
        }
    }

    data class HomeScreenUIState(
        var wallet: Wallet? = null,
        val balance: String? = null,
    )
}