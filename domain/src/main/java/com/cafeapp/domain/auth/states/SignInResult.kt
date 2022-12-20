package com.cafeapp.domain.auth.states

import com.cafeapp.domain.models.User

sealed class SignInResult {
    data class Success(val user: User) : SignInResult()
    object WrongCredentialsError : SignInResult()
    object NetworkUnavailableError : SignInResult()
    object OtherError : SignInResult()
}
