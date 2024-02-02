package com.example.familybudget.ui.model

data class Plan(
    val id: Int,
    val imageUrl: String,
    val nameOfPayment: String,
    val isDone: Boolean = false,
)