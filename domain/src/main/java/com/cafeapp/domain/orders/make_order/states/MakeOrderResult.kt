package com.cafeapp.domain.orders.make_order.states

sealed interface MakeOrderResult {
    object Success : MakeOrderResult

    object UserNotAuthenticated : MakeOrderResult

    object NetworkError : MakeOrderResult

    object OtherError : MakeOrderResult
}