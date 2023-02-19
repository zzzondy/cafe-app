package com.cafeapp.data.cart.repository

import com.cafeapp.data.cart.local.repository.LocalCartRepository
import com.cafeapp.data.cart.remote.RemoteCartRepository
import com.cafeapp.data.cart.util.toDomain
import com.cafeapp.data.cart.util.toDomainWithCount
import com.cafeapp.data.cart.util.toLocalCartFood
import com.cafeapp.domain.cart.repository.CartRepository
import com.cafeapp.domain.cart.states.CartTransactionsResult
import com.cafeapp.domain.cart.states.IncrementResult
import com.cafeapp.domain.cart.states.ObtainingCartResult
import com.cafeapp.domain.models.Food

class CartRepositoryImpl(
    private val remoteCartRepository: RemoteCartRepository,
    private val localCartRepository: LocalCartRepository
) : CartRepository {

    override suspend fun addFoodToCart(
        foodId: Long,
        userId: String
    ): CartTransactionsResult {
        return remoteCartRepository.addFoodToCart(foodId, userId).toDomain()
    }

    override suspend fun addFoodToLocalCart(food: Food): CartTransactionsResult {
        return try {
            localCartRepository.addFoodToLocalCart(food.toLocalCartFood())
            CartTransactionsResult.Success
        } catch (e: Exception) {
            CartTransactionsResult.OtherError
        }
    }

    override suspend fun getUserCart(userId: String): ObtainingCartResult {
        return remoteCartRepository.getUserCart(userId).toDomain()
    }

    override suspend fun getLocalUserCart(): ObtainingCartResult {
        return try {
            ObtainingCartResult.Success(
                localCartRepository.getLocalCart().map { it.toDomainWithCount() }
            )
        } catch (e: Exception) {
            ObtainingCartResult.OtherError
        }
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

    override suspend fun deleteFoodFromLocalCart(foodId: Long): CartTransactionsResult {
        return try {
            localCartRepository.deleteFoodFromCart(foodId)
            CartTransactionsResult.Success
        } catch (e: Exception) {
            CartTransactionsResult.OtherError
        }
    }

    override suspend fun deleteSelectedFoodFromCart(
        userId: String,
        foodIds: List<Long>
    ): CartTransactionsResult =
        remoteCartRepository.deleteSelectedFoodFromCart(userId, foodIds).toDomain()
}