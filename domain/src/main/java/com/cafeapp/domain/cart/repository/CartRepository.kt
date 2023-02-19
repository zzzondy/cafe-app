package com.cafeapp.domain.cart.repository

import com.cafeapp.domain.cart.states.CartTransactionsResult
import com.cafeapp.domain.cart.states.IncrementResult
import com.cafeapp.domain.cart.states.ObtainingCartResult

interface CartRepository {

    suspend fun addFoodToCart(foodId: Long, userId: String): CartTransactionsResult

    suspend fun addFoodToLocalCart(foodId: Long): CartTransactionsResult

    suspend fun getUserCart(userId: String): ObtainingCartResult

    suspend fun incrementItemsCount(userId: String, foodId: Long): IncrementResult

    suspend fun decrementItemsCount(userId: String, foodId: Long): IncrementResult

    suspend fun deleteFoodFromCart(userId: String, foodId: Long): CartTransactionsResult

    suspend fun deleteSelectedFoodFromCart(userId: String, foodIds: List<Long>): CartTransactionsResult
}