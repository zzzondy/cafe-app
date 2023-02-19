package com.cafeapp.domain.cart.usecase

import com.cafeapp.domain.auth.manager.UserManager
import com.cafeapp.domain.cart.repository.CartRepository

class AddFoodToCartUseCase(
    private val cartRepository: CartRepository,
    private val userManager: UserManager
) {
    suspend operator fun invoke(foodId: Long) {
        if (userManager.currentUser == null) {
            cartRepository.addFoodToLocalCart(foodId)
        } else {
            cartRepository.addFoodToCart(foodId, userManager.currentUser!!.id)
        }
    }
}