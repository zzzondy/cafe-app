package com.cafeapp.domain.auth.states.validation

sealed class ValidationEmailResult {
    object Success : ValidationEmailResult()

    object EmailIsEmpty : ValidationEmailResult()
    object NotValidEmail : ValidationEmailResult()
}
