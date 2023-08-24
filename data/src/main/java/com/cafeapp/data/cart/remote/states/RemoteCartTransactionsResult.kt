package com.cafeapp.data.cart.remote.states

sealed class RemoteCartTransactionsResult {
    object Success : RemoteCartTransactionsResult()
    object NetworkError : RemoteCartTransactionsResult()
    object OtherError : RemoteCartTransactionsResult()
}
