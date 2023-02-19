package com.cafeapp.domain.cart.usecase

import com.cafeapp.domain.auth.manager.UserManager
import com.cafeapp.domain.cart.repository.CartRepository
import com.cafeapp.domain.models.Food

class AddFoodToCartUseCase(
    private val cartRepository: CartRepository,
    private val userManager: UserManager
) {
    suspend operator fun invoke(food: Food) {
        if (userManager.currentUser != null) {
            cartRepository.addFoodToCart(food.id, userManager.currentUser!!.id)
        } else {
            cartRepository.addFoodToLocalCart(food)
        }
    }
}