package com.cafeapp.domain.make_order.states

import com.cafeapp.domain.make_order.models.PaymentMethod

sealed class ObtainingPaymentMethodsResult {
    data class Success(val data: List<PaymentMethod>) : ObtainingPaymentMethodsResult()
    object NetworkError : ObtainingPaymentMethodsResult()
    object OtherError : ObtainingPaymentMethodsResult()
}
