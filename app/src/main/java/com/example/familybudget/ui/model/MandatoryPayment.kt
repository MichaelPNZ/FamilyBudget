package com.example.familybudget.ui.model

data class MandatoryPayment(
    val id: Int,
    val imageUrl: String,
    val nameOfPayment: String,
    val amountOfPayment: Int,
    val isDone: Boolean = false,
)