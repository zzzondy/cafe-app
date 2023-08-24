package com.cafeapp.domain.orders.make_order.usecase

import com.cafeapp.domain.auth.manager.UserManager
import com.cafeapp.domain.auth.states.ObtainingUserPhoneNumberResult
import com.cafeapp.domain.orders.models.OrderDetails
import com.cafeapp.domain.orders.make_order.repository.MakeOrderRepository
import com.cafeapp.domain.orders.make_order.states.MakeOrderResult

class MakeOrderUseCase(
    private val userManager: UserManager,
    private val makeOrderRepository: MakeOrderRepository
) {
    suspend operator fun invoke(orderDetails: OrderDetails): MakeOrderResult {
        var tempOrderDetails = orderDetails.copy()

        if (userManager.currentUser == null) {
            return MakeOrderResult.UserNotAuthenticated
        } else {
            tempOrderDetails = tempOrderDetails.copy(userId = userManager.currentUser!!.id)
        }


        return when (val result = userManager.getUserPhoneNumber()) {
            is ObtainingUserPhoneNumberResult.Success -> {
                tempOrderDetails =
                    tempOrderDetails.copy(phoneNumber = result.phoneNumber)

                makeOrderRepository.makeOrder(tempOrderDetails)
            }

            is ObtainingUserPhoneNumberResult.UserNotAuthenticatedError -> MakeOrderResult.UserNotAuthenticated

            is ObtainingUserPhoneNumberResult.NetworkError -> MakeOrderResult.NetworkError

            is ObtainingUserPhoneNumberResult.OtherError -> MakeOrderResult.OtherError
        }

    }
}