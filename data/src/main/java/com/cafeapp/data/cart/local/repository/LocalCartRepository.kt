package com.cafeapp.data.cart.local.repository

import com.cafeapp.data.cart.local.models.LocalCartFood

interface LocalCartRepository {

    suspend fun getLocalCart(): List<LocalCartFood>

    suspend fun addFoodToLocalCart(food: LocalCartFood): Long

    suspend fun deleteFoodFromCart(foodId: Long)
}