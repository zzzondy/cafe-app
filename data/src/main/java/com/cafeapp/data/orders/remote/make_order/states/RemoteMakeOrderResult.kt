package com.cafeapp.data.orders.remote.make_order.states

sealed interface RemoteMakeOrderResult {
    object Success : RemoteMakeOrderResult

    object NetworkError : RemoteMakeOrderResult

    object OtherError : RemoteMakeOrderResult
}