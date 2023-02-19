package com.cafeapp.domain.cart.usecase

import com.cafeapp.domain.auth.manager.UserManager
import com.cafeapp.domain.cart.repository.CartRepository
import com.cafeapp.domain.cart.states.IncrementResult

class DecrementItemsCountUseCase(
    private val cartRepository: CartRepository,
    private val userManager: UserManager
) {
    suspend operator fun invoke(foodId: Long, currentCount: Int): IncrementResult {
        if (currentCount < 1) {
            return IncrementResult.Success(1)
        }
        return if (userManager.currentUser != null) {
            cartRepository.decrementItemsCount(userManager.currentUser!!.id, foodId)
        } else {
            TODO()
        }
    }
}