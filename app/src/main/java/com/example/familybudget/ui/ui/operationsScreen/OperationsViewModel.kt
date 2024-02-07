package com.example.familybudget.ui.ui.operationsScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.familybudget.ui.data.WalletsRepository
import com.example.familybudget.ui.model.Operation
import com.example.familybudget.ui.model.Wallet
import kotlinx.coroutines.launch

class OperationsViewModel(application: Application) : AndroidViewModel(application) {

    private val appContext = application
    private val walletsRepository = WalletsRepository(appContext)
    private val _operationsUIState = MutableLiveData<OperationsUIState>()
    val operationsUIState: LiveData<OperationsUIState>
        get() = _operationsUIState

    fun loadOperations(walletId: Int) {
        viewModelScope.launch {
            val walletCache = walletsRepository.getWalletById(walletId)
            if (walletCache != null) {
                _operationsUIState.postValue(
                    OperationsUIState(
                    wallet = walletCache,
                )
                )
            }
        }
    }

    fun onAddOperationClicked(amount: String, selectedIcon: CharSequence, place: String) {
        val category = when (selectedIcon) {
            "Банк" -> "bank"
            "Подписки" -> "dollar"
            "Спорт" -> "gym"
            "Дом" -> "home"
            "Детский сад" -> "kindergarten"
            "Телефон" -> "phone"
            "Школа" -> "school"
            "Магазин" -> "store"
            else -> {
                "error"
            }
        }
        val currentOperationsUIState = _operationsUIState.value ?: return
        val wallet = currentOperationsUIState.wallet ?: return
        val lastId = wallet.operations.lastOrNull()?.id ?: -1
        if (lastId >= 0) {
            updateUI(
                wallet,
                lastId + 1,
                walletsRepository.getCurrentDate(),
                category,
                selectedIcon.toString(),
                place,
                amount.toInt()
            )
        } else {
            updateUI(
                wallet,
                0,
                walletsRepository.getCurrentDate(),
                category,
                selectedIcon.toString(),
                place,
                amount.toInt()
            )
        }
    }

    private fun updateUI(
        wallet: Wallet,
        id: Int,
        date: String,
        imageUrl: String,
        nameOfCategory: String,
        nameOfPlace: String,
        amount: Int
    ) {
        val operations = wallet.operations.plus(
            Operation(
                id,
                date,
                "$imageUrl.png",
                nameOfCategory,
                nameOfPlace,
                amount
            )
        )
        val updateList = wallet.copy(operations = operations)
        _operationsUIState.value = OperationsUIState(
            wallet = updateList
        )
        viewModelScope.launch {
            walletsRepository.insertWallet(updateList)
        }
    }

    data class OperationsUIState(
        val wallet: Wallet? = null,
    )
}