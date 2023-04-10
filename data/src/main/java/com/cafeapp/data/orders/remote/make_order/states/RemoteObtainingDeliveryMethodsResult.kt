package com.cafeapp.data.orders.remote.make_order.states

import com.cafeapp.data.orders.remote.models.RemoteDeliveryMethod

sealed class RemoteObtainingDeliveryMethodsResult {

    data class Success(val data: List<RemoteDeliveryMethod>) :
        RemoteObtainingDeliveryMethodsResult()

    object NetworkError : RemoteObtainingDeliveryMethodsResult()

    object OtherError : RemoteObtainingDeliveryMethodsResult()
}
