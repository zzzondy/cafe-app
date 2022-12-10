package com.cafeapp.ui.screens.profile.login.states

import com.cafeapp.domain.models.User

sealed class LoginScreenState {
    data class SuccessfullySignIn(val user: User) : LoginScreenState()
    object WrongCredentialsError : LoginScreenState()
    object NetworkUnavailable : LoginScreenState()
    object Initially : LoginScreenState()
}
