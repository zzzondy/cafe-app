package com.cafeapp.domain.cart.usecase

import com.cafeapp.domain.auth.manager.UserManager
import com.cafeapp.domain.cart.repository.CartRepository
import com.cafeapp.domain.cart.states.IncrementResult

class IncrementItemsCountUseCase(private val cartRepository: CartRepository, private val userManager: UserManager) {

    suspend operator fun invoke(foodId: Long): IncrementResult {
        return if (userManager.currentUser != null) {
            cartRepository.incrementItemsCount(userManager.currentUser!!.id, foodId)
        } else {
            TODO()
        }
    }
}