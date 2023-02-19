package com.cafeapp.data.cart.remote

import com.cafeapp.data.cart.remote.states.RemoteCartTransactionsResult
import com.cafeapp.data.cart.remote.states.RemoteIncrementResult
import com.cafeapp.data.cart.remote.states.RemoteObtainingCartResult

interface RemoteCartRepository {
    suspend fun addFoodToCart(foodId: Long, userId: String): RemoteCartTransactionsResult
    suspend fun getUserCart(userId: String): RemoteObtainingCartResult
    suspend fun incrementItemsCount(userId: String, foodId: Long): RemoteIncrementResult
    suspend fun decrementItemsCount(userId: String, foodId: Long): RemoteIncrementResult
    suspend fun deleteFoodFromCart(userId: String, foodId: Long): RemoteCartTransactionsResult
}