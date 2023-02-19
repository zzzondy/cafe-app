package com.cafeapp.domain.cart.states

sealed class CartTransactionsResult {
    object Success : CartTransactionsResult()
    object NetworkError : CartTransactionsResult()
    object OtherError : CartTransactionsResult()
}