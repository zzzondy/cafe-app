package com.cafeapp.ui.screens.profile.signUp_flow.signUp.states

sealed class SignUpScreenState {
    object Initially : SignUpScreenState()
    data class Success(val email: String, val password: String) : SignUpScreenState()
    object NetworkUnavailableError : SignUpScreenState()
    object UserAlreadyExistsError : SignUpScreenState()
    object OtherError : SignUpScreenState()
}
