package com.cafeapp.domain.cart.states

sealed class IncrementResult {
    data class Success(val count: Int) : IncrementResult()
    object NetworkError : IncrementResult()
    object OtherError : IncrementResult()
}
