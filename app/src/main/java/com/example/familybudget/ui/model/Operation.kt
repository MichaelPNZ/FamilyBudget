package com.example.familybudget.ui.model


data class Operation(
    val id: Int,
    val date: String,
    val imageUrl: String,
    val nameOfCategory: String,
    val nameOfPlace: String,
    val amount: Int,
    )