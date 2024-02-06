package com.example.familybudget.ui.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.familybudget.ui.model.Wallet

@Dao
interface WalletsDao {

    @Query("SELECT * FROM wallet")
    fun getWallets(): List<Wallet>?

    @Query("SELECT * FROM wallet WHERE id = :walletId")
    fun getWalletById(walletId: Int): Wallet?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWalletsList(wallets: List<Wallet>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWallet(wallet: Wallet)

    @Delete
    fun deleteWallet(wallet: Wallet)

}