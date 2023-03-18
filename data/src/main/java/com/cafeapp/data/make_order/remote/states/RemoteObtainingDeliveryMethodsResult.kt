package com.cafeapp.data.make_order.remote.states

import com.cafeapp.data.make_order.remote.models.RemoteDeliveryMethod

sealed class RemoteObtainingDeliveryMethodsResult {

    data class Success(val data: List<RemoteDeliveryMethod>) :
        RemoteObtainingDeliveryMethodsResult()

    object NetworkError : RemoteObtainingDeliveryMethodsResult()

    object OtherError : RemoteObtainingDeliveryMethodsResult()
}
