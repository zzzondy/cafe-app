package com.cafeapp.data.cart.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cafeapp.data.cart.local.CartDatabaseContract
import com.cafeapp.data.cart.local.database.daos.LocalCartDao
import com.cafeapp.data.cart.local.models.LocalCartFood

@Database(entities = [LocalCartFood::class], version = CartDatabaseContract.DATABASE_VERSION)
abstract class CartDatabase : RoomDatabase() {
    abstract val localCartDao: LocalCartDao

    companion object {
        fun create(context: Context): CartDatabase = Room.databaseBuilder(
            context,
            CartDatabase::class.java,
            CartDatabaseContract.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }
}