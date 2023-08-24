package com.cafeapp.domain.orders.make_order.states

import com.cafeapp.domain.orders.models.DeliveryMethod

sealed class ObtainingDeliveryMethodsResult {

    data class Success(val data: List<DeliveryMethod>) : ObtainingDeliveryMethodsResult()

    object NetworkError : ObtainingDeliveryMethodsResult()

    object OtherError : ObtainingDeliveryMethodsResult()
}
