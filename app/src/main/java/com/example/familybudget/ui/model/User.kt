package com.example.familybudget.ui.model

data class User(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val wallets: List<Wallet>,
    val plans: List<Plan>,
)