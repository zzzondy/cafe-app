package com.cafeapp.domain.cart.usecase

import com.cafeapp.domain.cart.repository.CartRepository

class CreateCartForUserUseCase(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(userId: String) {
        cartRepository.createCartForUser(userId)
    }
}