package com.cafeapp.data.orders.remote.orders_list.states

import com.cafeapp.data.orders.remote.models.RemoteOrderWithFood

sealed class RemoteObtainingOrdersResult {

    data class Success(val data: List<RemoteOrderWithFood>) : RemoteObtainingOrdersResult()

    object NetworkError : RemoteObtainingOrdersResult()

    object OtherError : RemoteObtainingOrdersResult()
}
