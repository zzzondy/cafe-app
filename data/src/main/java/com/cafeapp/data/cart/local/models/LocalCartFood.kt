package com.cafeapp.data.cart.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.cafeapp.data.cart.local.CartDatabaseContract

@Entity(
    tableName = CartDatabaseContract.LocalCart.TABLE_NAME,
    indices = [Index(CartDatabaseContract.LocalCart.ID)]
)
data class LocalCartFood(
    @ColumnInfo(name = CartDatabaseContract.LocalCart.ID)
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = CartDatabaseContract.LocalCart.NAME)
    val name: String,

    @ColumnInfo(name = CartDatabaseContract.LocalCart.DESCRIPTION)
    val description: String,

    @ColumnInfo(name = CartDatabaseContract.LocalCart.PRICE)
    val price: Int,

    @ColumnInfo(name = CartDatabaseContract.LocalCart.IMAGE_URL)
    val imageUrl: String,

    @ColumnInfo(name = CartDatabaseContract.LocalCart.COUNT)
    val count: Int = 1
)
