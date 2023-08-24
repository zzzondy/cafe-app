package com.cafeapp.domain.cart.usecase

import com.cafeapp.domain.auth.manager.UserManager
import com.cafeapp.domain.cart.repository.CartRepository
import com.cafeapp.domain.cart.states.CartTransactionsResult
import com.cafeapp.domain.models.Food

class DeleteSelectedFoodUseCase(
    private val userManager: UserManager,
    private val cartRepository: CartRepository
) {

    suspend operator fun invoke(foodIds: List<Pair<Food, Int>>): CartTransactionsResult {
        return if (userManager.currentUser != null) {
            cartRepository.deleteSelectedFoodFromCart(
                userManager.currentUser!!.id,
                foodIds.map { it.first.id })
        } else {
            CartTransactionsResult.Success
        }
    }
}