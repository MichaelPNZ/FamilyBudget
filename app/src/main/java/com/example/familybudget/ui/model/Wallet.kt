package com.example.familybudget.ui.model

data class Wallet(
    val id: Int,
    val name: String,
    val currentMonth: String,
    val monthlyIncome: Int,
    val monthlyExpenses: Int,
    val operations: List<Operation>,
    val mandatoryPayments: List<MandatoryPayment>,
    )