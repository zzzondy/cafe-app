package com.cafeapp.domain.auth.states

import com.cafeapp.domain.models.User

sealed class SignUpResult {
    data class Success(val user: User) : SignUpResult()
    object NetworkUnavailableError : SignUpResult()
    object UserAlreadyExistsError : SignUpResult()
    object OtherError : SignUpResult()
}
