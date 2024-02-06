package com.example.familybudget.ui.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Entity
data class Wallet(
    @PrimaryKey
    @ColumnInfo(name = "id")val id: Int = 0,
    @ColumnInfo(name = "name")val name: String = "Семейный бюджет",
    @ColumnInfo(name = "currentMonth")val currentMonth: String = "",
    @ColumnInfo(name = "monthlyIncome")val monthlyIncome: Int = 0,
    @ColumnInfo(name = "monthlyExpenses")val monthlyExpenses: Int = 0,
    @ColumnInfo(name = "operations")val operations: List<Operation> = listOf(),
    @ColumnInfo(name = "mandatoryPayments")val mandatoryPayments: List<MandatoryPayment> =
        listOf(),
)

class Converters {
    @TypeConverter
    fun fromOperationsList(list: List<Operation>?): String {
        return Json.encodeToString(list ?: emptyList())
    }

    @TypeConverter
    fun toOperationsList(value: String): List<Operation> {
        return try {
            Json.decodeFromString(value)
        } catch (e: Exception) {
            emptyList()
        }
    }

    @TypeConverter
    fun fromMandatoryPaymentList(list: List<MandatoryPayment>?): String {
        return Json.encodeToString(list ?: emptyList())
    }

    @TypeConverter
    fun toMandatoryPaymentList(value: String): List<MandatoryPayment> {
        return try {
            Json.decodeFromString(value)
        } catch (e: Exception) {
            emptyList()
        }
    }
}