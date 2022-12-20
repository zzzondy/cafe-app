package com.cafeapp.domain.auth.states.validation

sealed class ValidationPasswordResult {
    object Success : ValidationPasswordResult()

    object VeryShortPassword : ValidationPasswordResult()
    object NotContainsLettersOrDigits : ValidationPasswordResult()
    object NotContainsLowerOrUpperCase : ValidationPasswordResult()
}
