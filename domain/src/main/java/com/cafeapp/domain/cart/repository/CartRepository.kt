package com.cafeapp.domain.cart.repository

interface CartRepository {
    suspend fun createCartForUser(userId: String)
}