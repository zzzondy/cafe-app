package com.cafeapp.domain.orders.orders_list.usecase

import com.cafeapp.domain.auth.manager.UserManager
import com.cafeapp.domain.orders.orders_list.repository.OrdersListRepository
import com.cafeapp.domain.orders.orders_list.states.ObtainingOrdersListResult

class ObtainOrdersForUserUseCase(
    private val userManager: UserManager,
    private val ordersListRepository: OrdersListRepository
) {

    suspend operator fun invoke(): ObtainingOrdersListResult {
        return if (userManager.currentUser == null) {
            ObtainingOrdersListResult.UserIsNotAuthenticatedError
        } else {
            ordersListRepository.getOrdersForUser(userManager.currentUser!!.id)
        }
    }
}