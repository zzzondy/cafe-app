package com.cafeapp.ui.screens.profile.login.states

sealed class LoginScreenState {
    object SuccessfullySignIn : LoginScreenState()
    object WrongCredentialsError : LoginScreenState()
    object NetworkUnavailable : LoginScreenState()
    object Initially : LoginScreenState()
}
