package com.cafeapp.domain.orders.make_order.states

import com.cafeapp.domain.orders.models.PaymentMethod

sealed class ObtainingPaymentMethodsResult {
    data class Success(val data: List<PaymentMethod>) : ObtainingPaymentMethodsResult()
    object NetworkError : ObtainingPaymentMethodsResult()
    object OtherError : ObtainingPaymentMethodsResult()
}
