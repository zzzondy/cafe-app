package com.cafeapp.data.make_order.remote.models

sealed interface RemoteMakeOrderResult {
    object Success : RemoteMakeOrderResult

    object NetworkError : RemoteMakeOrderResult

    object OtherError : RemoteMakeOrderResult
}