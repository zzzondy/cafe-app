package com.cafeapp.domain.cart.usecase

import com.cafeapp.domain.auth.manager.UserManager
import com.cafeapp.domain.cart.repository.CartRepository
import com.cafeapp.domain.cart.states.ObtainingCartResult

class ObtainUserCartUseCase(
    private val cartRepository: CartRepository,
    private val userManager: UserManager
) {
    suspend operator fun invoke(): ObtainingCartResult {
        return if (userManager.currentUser != null) {
            cartRepository.getUserCart(userManager.currentUser!!.id)
        } else {
            cartRepository.getLocalUserCart()
        }
    }
}