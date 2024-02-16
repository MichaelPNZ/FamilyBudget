package com.example.familybudget.ui.data

import android.content.Context
import androidx.room.Room
import com.example.familybudget.ui.model.Wallet
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class WalletsRepository(context: Context) {

    private val db: AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "database-categories"
    )
        .fallbackToDestructiveMigration()
        .build()

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val walletsDao = db.walletsDao()

    suspend fun getWallets() : List<Wallet>? {
        return withContext(ioDispatcher) {
            walletsDao.getWallets()
        }
    }

    suspend fun getWalletById(walletId: Int): Wallet? {
        return withContext(ioDispatcher) {
            walletsDao.getWalletById(walletId)
        }
    }

    suspend fun insertWalletsList(wallets: List<Wallet>) {
        return withContext(ioDispatcher) {
            walletsDao.insertWalletsList(wallets)
        }
    }

    suspend fun insertWallet(wallet: Wallet) {
        return withContext(ioDispatcher) {
            walletsDao.insertWallet(wallet)
        }
    }

    suspend fun deleteWallet(wallet: Wallet) {
        return withContext(ioDispatcher) {
            walletsDao.deleteWallet(wallet)
        }
    }

    fun getCurrentMonth(): String {
        val currentDate = Calendar.getInstance().time
        val monthFormat = SimpleDateFormat("LLLL", Locale("ru"))
        return monthFormat.format(currentDate)
    }

    fun getCurrentDate(): String {
        val currentDate = LocalDate.now(ZoneId.of("Europe/Moscow"))
        val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("ru"))
        return dateFormatter.format(currentDate)
    }

}