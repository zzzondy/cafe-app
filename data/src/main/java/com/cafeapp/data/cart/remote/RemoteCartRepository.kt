package com.cafeapp.data.cart.remote

interface RemoteCartRepository {
    suspend fun createCartForUser(userId: String)
}