package com.cafeapp.ui.screens.profile.signUp.states

sealed class SignUpScreenState {
    object Initially : SignUpScreenState()
    object Success : SignUpScreenState()
    object NetworkUnavailableError : SignUpScreenState()
    object UserAlreadyExistsError : SignUpScreenState()
    object OtherError : SignUpScreenState()
}
