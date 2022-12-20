package com.cafeapp.data.cart.repository

import com.cafeapp.data.cart.remote.RemoteCartRepository
import com.cafeapp.domain.cart.repository.CartRepository

class CartRepositoryImpl(private val remoteCartRepository: RemoteCartRepository) : CartRepository {

    override suspend fun createCartForUser(userId: String) {
        remoteCartRepository.createCartForUser(userId)
    }
}