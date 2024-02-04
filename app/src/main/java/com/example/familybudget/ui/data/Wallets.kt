package com.example.familybudget.ui.data

import com.example.familybudget.ui.model.MandatoryPayment
import com.example.familybudget.ui.model.Operation
import com.example.familybudget.ui.model.Wallet
import java.time.LocalDateTime
import java.util.Calendar

object STUB {

    fun getWallet(walletId: Int): Wallet? =
        if (walletId == wallets[walletId].id) wallets[walletId]
        else null

    fun getAllWallets(): List<Wallet> = wallets

    fun getOperations(walletId: Int): List<Operation>? =
        wallets.find { it.id == walletId }?.operations

    fun getMandatoryPayments(walletId: Int): List<MandatoryPayment>? =
        wallets.find { it.id == walletId }?.mandatoryPayments

    fun getBalance(walletId: Int): String {
        val currentWallet = getWallet(walletId)
        val balance =
            if (currentWallet != null) {currentWallet.monthlyIncome - currentWallet.monthlyExpenses}
        else { 0 }
        return balance.toString()
    }

    private val calendar = Calendar.getInstance()
    private val current = LocalDateTime.of(
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH),
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        calendar.get(Calendar.SECOND)
    )

    private val wallets = listOf(
        Wallet(
            id = 0,
            "Семейный бюджет",
            currentMonth = current.month.name,
            monthlyIncome = 30000,
            monthlyExpenses = 12000,
            operations = listOf(
                Operation(
                    0,
                    current.toString(),
                    "store.png",
                    "Покупка в магазине",
                    "Цитрон",
                    555,
                ),
                Operation(
                    1,
                    current.toString(),
                    "store.png",
                    "Покупка в магазине",
                    "Хоббиты",
                    1300,
                ),
                Operation(
                    2,
                    current.toString(),
                    "store.png",
                    "Покупка в магазине",
                    "Магнит",
                    2654,
                )
            ),
            mandatoryPayments = listOf(
                MandatoryPayment(
                    0,
                    "home.png",
                    "Квартплата",
                    7500,
                    false
                ),
                MandatoryPayment(
                    1,
                    "phone.png",
                    "Сотовая связь",
                    2000,
                    false
                ),
                MandatoryPayment(
                    2,
                    "gym.png",
                    "Спорт зал",
                    5500,
                    false
                ),
                MandatoryPayment(
                    3,
                    "bank.png",
                    "Кредит",
                    9000,
                    false
                ),
                MandatoryPayment(
                    4,
                    "dollar.png",
                    "Подписки",
                    800,
                    false
                ),
                MandatoryPayment(
                    5,
                    "school.png",
                    "Школа",
                    3200,
                    false
                ),
                MandatoryPayment(
                    6,
                    "kindergarten.png",
                    "Детский сад",
                    2000,
                    false
                ),
                MandatoryPayment(
                    7,
                    "dollar.png",
                    "Налог",
                    8000,
                    false
                )
            )
        ),
        Wallet(
            id = 1,
            "Ntcn бюджет",
            currentMonth = current.month.name,
            monthlyIncome = 7777770,
            monthlyExpenses = 6222000,
            operations = listOf(
                Operation(
                    0,
                    current.toString(),
                    "store.png",
                    "Покупка в магазине",
                    "Цитрон",
                    555,
                ),
                Operation(
                    1,
                    current.toString(),
                    "store.png",
                    "Покупка в магазине",
                    "Хоббиты",
                    1300,
                ),
                Operation(
                    2,
                    current.toString(),
                    "store.png",
                    "Покупка в магазине",
                    "Магнит",
                    2654,
                )
            ),
            mandatoryPayments = listOf(
                MandatoryPayment(
                    0,
                    "home.png",
                    "Квартплата",
                    7500,
                    false
                ),
                MandatoryPayment(
                    1,
                    "phone.png",
                    "Сотовая связь",
                    2000,
                    false
                ),
                MandatoryPayment(
                    2,
                    "gym.png",
                    "Спорт зал",
                    5500,
                    false
                ),
                MandatoryPayment(
                    3,
                    "bank.png",
                    "Кредит",
                    9000,
                    false
                ),
                MandatoryPayment(
                    4,
                    "dollar.png",
                    "Подписки",
                    800,
                    false
                ),
                MandatoryPayment(
                    5,
                    "school.png",
                    "Школа",
                    3200,
                    false
                ),
                MandatoryPayment(
                    6,
                    "kindergarten.png",
                    "Детский сад",
                    2000,
                    false
                ),
                MandatoryPayment(
                    7,
                    "dollar.png",
                    "Налог",
                    8000,
                    false
                )
            )
        )
    )

}