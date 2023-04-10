package com.cafeapp.data.orders.remote.make_order.states

import com.cafeapp.data.orders.remote.models.RemotePaymentMethod

sealed class RemoteObtainingMethodPaymentsResult {
    data class Success(val data: List<RemotePaymentMethod>) : RemoteObtainingMethodPaymentsResult()
    object NetworkError : RemoteObtainingMethodPaymentsResult()
    object OtherError : RemoteObtainingMethodPaymentsResult()
}
