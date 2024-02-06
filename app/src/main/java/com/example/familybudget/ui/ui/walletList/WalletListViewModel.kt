package com.example.familybudget.ui.ui.walletList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.familybudget.ui.data.WalletsRepository
import com.example.familybudget.ui.model.Wallet
import kotlinx.coroutines.launch

class WalletListViewModel(application: Application): AndroidViewModel(application) {

    private val appContext = application
    private val walletsRepository = WalletsRepository(appContext)
    private val _walletListUIState = MutableLiveData<WalletListUIState>()
    val walletListUIState: LiveData<WalletListUIState>
        get() = _walletListUIState

    fun loadUser(walletId: Int) {
        viewModelScope.launch {
            val allWalletCache = walletsRepository.getWallets() ?: return@launch
            updateUI(walletId, allWalletCache)
        }
    }

    fun onAddNewWalletClicked(name: String) {
        val currentHomeScreenUIState = _walletListUIState.value ?: return
        val listWallet = currentHomeScreenUIState.listWallet ?: return
        val lastId = listWallet.lastOrNull()?.id ?: 0
        val update = Wallet(
            id = lastId + 1,
            name = name,
            currentMonth = walletsRepository.getCurrentMonth()
        )
        viewModelScope.launch {
            val updateList = currentHomeScreenUIState.copy(
                listWallet = listWallet.plus(update)
            )
            _walletListUIState.postValue(updateList)
            walletsRepository.insertWallet(update)
        }
    }

    fun deleteWallet(walletId: Int) {
        val wallets = _walletListUIState.value?.listWallet ?: return
        val walletToDelete = wallets.find { it.id == walletId } ?: return
        viewModelScope.launch {
            walletsRepository.deleteWallet(walletToDelete)
            _walletListUIState.postValue(_walletListUIState.value?.copy(listWallet = wallets.minus(walletToDelete)))
        }
    }

    private fun updateUI(walletId: Int, walletList: List<Wallet>) {
        _walletListUIState.postValue(
            WalletListUIState(
                currentId = walletId,
                listWallet = walletList
            )
        )
    }

    data class WalletListUIState(
        val currentId: Int? = null,
        val listWallet: List<Wallet>? = null,
    )
}