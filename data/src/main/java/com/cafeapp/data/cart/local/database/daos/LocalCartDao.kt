package com.cafeapp.data.cart.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cafeapp.data.cart.local.CartDatabaseContract
import com.cafeapp.data.cart.local.models.LocalCartFood

@Dao
interface LocalCartDao {

    @Query("SELECT * FROM ${CartDatabaseContract.LocalCart.TABLE_NAME} ORDER BY ${CartDatabaseContract.LocalCart.ID} DESC")
    suspend fun getCart(): List<LocalCartFood>

    @Insert
    suspend fun addFoodToCart(food: LocalCartFood): Long

    @Query("DELETE FROM ${CartDatabaseContract.LocalCart.TABLE_NAME} WHERE ${CartDatabaseContract.LocalCart.ID} = :foodId")
    suspend fun deleteFoodFromCart(foodId: Long)
}