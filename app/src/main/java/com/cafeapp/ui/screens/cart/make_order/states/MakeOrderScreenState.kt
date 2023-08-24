package com.cafeapp.ui.screens.cart.make_order.states

import com.cafeapp.domain.orders.models.PaymentMethod
import com.cafeapp.domain.orders.models.DeliveryMethod

sealed class MakeOrderScreenState {
    object Loading : MakeOrderScreenState()

    data class ScreenData(
        val total: Int,
        val paymentMethods: List<PaymentMethod>,
        val deliveryMethods: List<DeliveryMethod>,
        val isCheckoutButtonAvailable: Boolean
    ) :
        MakeOrderScreenState()

    object NetworkError : MakeOrderScreenState()

    object OtherError : MakeOrderScreenState()
}
