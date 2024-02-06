package com.example.familybudget.ui.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.familybudget.ui.model.Converters
import com.example.familybudget.ui.model.Wallet

@Database(entities = [Wallet::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun walletsDao(): WalletsDao

}