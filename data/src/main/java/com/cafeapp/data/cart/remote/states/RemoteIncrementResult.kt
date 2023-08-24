package com.cafeapp.data.cart.remote.states

sealed class RemoteIncrementResult {
    data class Success(val count: Int) : RemoteIncrementResult()
    object NetworkError : RemoteIncrementResult()
    object OtherError : RemoteIncrementResult()
}
