package com.cafeapp.data.cart.repository

import com.cafeapp.data.cart.remote.RemoteCartRepository
import com.cafeapp.data.cart.util.toDomain
import com.cafeapp.domain.cart.repository.CartRepository
import com.cafeapp.domain.cart.states.CartTransactionsResult
import com.cafeapp.domain.cart.states.IncrementResult
import com.cafeapp.domain.cart.states.ObtainingCartResult

class CartRepositoryImpl(private val remoteCartRepository: RemoteCartRepository) : CartRepository {

    override suspend fun addFoodToCart(
        foodId: Long,
        userId: String
    ): CartTransactionsResult {
        return remoteCartRepository.addFoodToCart(foodId, userId).toDomain()
    }

    override suspend fun addFoodToLocalCart(foodId: Long): CartTransactionsResult {
        return CartTransactionsResult.Success // Todo: Implement
    }

    override suspend fun getUserCart(userId: String): ObtainingCartResult {
        return remoteCartRepository.getUserCart(userId).toDomain()
    }

    override suspend fun incrementItemsCount(userId: String, foodId: Long): IncrementResult {
        return remoteCartRepository.incrementItemsCount(userId, foodId).toDomain()
    }

    override suspend fun decrementItemsCount(userId: String, foodId: Long): IncrementResult {
        return remoteCartRepository.decrementItemsCount(userId, foodId).toDomain()
    }

    override suspend fun deleteFoodFromCart(userId: String, foodId: Long): CartTransactionsResult {
        return remoteCartRepository.deleteFoodFromCart(userId, foodId).toDomain()
    }
}