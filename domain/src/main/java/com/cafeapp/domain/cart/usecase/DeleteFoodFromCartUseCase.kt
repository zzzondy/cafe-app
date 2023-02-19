package com.cafeapp.domain.cart.usecase

import com.cafeapp.domain.auth.manager.UserManager
import com.cafeapp.domain.cart.repository.CartRepository
import com.cafeapp.domain.cart.states.CartTransactionsResult
import com.cafeapp.domain.models.Food

class DeleteFoodFromCartUseCase(
    private val userManager: UserManager,
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(food: Food): CartTransactionsResult {
        return if (userManager.currentUser != null) {
            cartRepository.deleteFoodFromCart(userManager.currentUser!!.id, food.id)
        } else {
            cartRepository.deleteFoodFromLocalCart(food.id)
        }
    }
}