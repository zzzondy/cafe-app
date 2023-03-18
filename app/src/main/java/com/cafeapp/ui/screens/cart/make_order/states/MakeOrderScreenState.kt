package com.cafeapp.ui.screens.cart.make_order.states

import com.cafeapp.domain.make_order.models.PaymentMethod
import com.cafeapp.domain.make_order.models.DeliveryMethod

sealed class MakeOrderScreenState {
    object Loading : MakeOrderScreenState()

    data class ScreenData(
        val total: Int,
        val paymentMethods: List<PaymentMethod>,
        val deliveryMethods: List<DeliveryMethod>
    ) :
        MakeOrderScreenState()

    object NetworkError : MakeOrderScreenState()

    object OtherError : MakeOrderScreenState()
}
