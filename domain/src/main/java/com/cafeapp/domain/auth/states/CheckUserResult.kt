package com.cafeapp.domain.auth.states

sealed class CheckUserResult {
    object AlreadyExists : CheckUserResult()
    object NotExists : CheckUserResult()
    object NetworkUnavailableError : CheckUserResult()
    object OtherError : CheckUserResult()
}