package com.cafeapp.data.cart.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
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

    @Transaction
    suspend fun incrementItemsCountAndGetResult(foodId: Long): Int {
        val itemsCount = getItemsCount(foodId)
        updateItemsCount(foodId, itemsCount + 1)
        return itemsCount + 1
    }

    @Transaction
    suspend fun decrementItemsCountAndGetResult(foodId: Long): Int {
        val itemsCount = getItemsCount(foodId)
        updateItemsCount(foodId, maxOf(1, itemsCount - 1))
        return maxOf(1, itemsCount - 1)
    }

    @Query("UPDATE ${CartDatabaseContract.LocalCart.TABLE_NAME} SET ${CartDatabaseContract.LocalCart.COUNT} = :updatedCount WHERE ${CartDatabaseContract.LocalCart.ID} = :foodId")
    suspend fun updateItemsCount(foodId: Long, updatedCount: Int)

    @Query("SELECT ${CartDatabaseContract.LocalCart.COUNT} FROM ${CartDatabaseContract.LocalCart.TABLE_NAME} WHERE ${CartDatabaseContract.LocalCart.ID} = :foodId")
    suspend fun getItemsCount(foodId: Long): Int
}