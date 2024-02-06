package com.example.familybudget.ui.model

import kotlinx.serialization.Serializable

@Serializable
data class MandatoryPayment(
    val id: Int,
    val imageUrl: String,
    val nameOfPayment: String,
    val amountOfPayment: Int,
    val isDone: Boolean = false,
)