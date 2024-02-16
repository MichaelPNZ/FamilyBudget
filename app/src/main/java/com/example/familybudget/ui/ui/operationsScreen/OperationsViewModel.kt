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
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

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
                onDayChanged(walletId, 0)
            }
        }
    }

    fun onDayChanged(walletId: Int, position: Int) {
        val currentDate = LocalDate.now(ZoneId.of("Europe/Moscow"))
        val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("ru"))

        viewModelScope.launch {
            val walletCache = walletsRepository.getWalletById(walletId)
            if (walletCache != null) {
                var filteredOperations  = listOf<Operation>()
                when (position) {
                    0 -> {
                        filteredOperations = walletCache.operations.filter { operation ->
                            val operationDate = LocalDate.parse(operation.date, dateFormatter)
                            operationDate == currentDate
                        }
                    }
                    1 -> {
                        filteredOperations = walletCache.operations.filter { operation ->
                            val operationDate = LocalDate.parse(operation.date, dateFormatter)
                            val startOfWeek = currentDate.minusWeeks(1).with(DayOfWeek.MONDAY) // Начало прошлой недели
                            val endOfWeek = currentDate.with(DayOfWeek.SUNDAY) // Конец текущей недели
                            operationDate.isAfter(startOfWeek) && operationDate.isBefore(endOfWeek.plusDays(1))
                        }
                    }
                    2 ->  {
                        filteredOperations = walletCache.operations.filter { operation ->
                            val operationDate = LocalDate.parse(operation.date, dateFormatter)
                            operationDate.month == currentDate.month
                        }
                    }
                    3 ->  {
                        filteredOperations = walletCache.operations
                    }
                }

                val updateList = walletCache.copy(operations = filteredOperations)
                _operationsUIState.postValue(
                    OperationsUIState(
                        wallet = updateList,
                    )
                )
            }
        }
    }

    fun onAddOperationClicked(amount: String, selectedIcon: CharSequence, place: String, date: String) {
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
                date,
                category,
                selectedIcon.toString(),
                place,
                amount
            )
        } else {
            updateUI(
                wallet,
                0,
                date,
                category,
                selectedIcon.toString(),
                place,
                amount
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
        amount: String
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