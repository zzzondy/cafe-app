package com.cafeapp.data.make_order.remote.states

import com.cafeapp.data.make_order.remote.models.RemotePaymentMethod

sealed class RemoteObtainingMethodPaymentsResult {
    data class Success(val data: List<RemotePaymentMethod>) : RemoteObtainingMethodPaymentsResult()
    object NetworkError : RemoteObtainingMethodPaymentsResult()
    object OtherError : RemoteObtainingMethodPaymentsResult()
}
