package com.cafeapp.domain.make_order.states

import com.cafeapp.domain.make_order.models.DeliveryMethod

sealed class ObtainingDeliveryMethodsResult {

    data class Success(val data: List<DeliveryMethod>) : ObtainingDeliveryMethodsResult()

    object NetworkError : ObtainingDeliveryMethodsResult()

    object OtherError : ObtainingDeliveryMethodsResult()
}
