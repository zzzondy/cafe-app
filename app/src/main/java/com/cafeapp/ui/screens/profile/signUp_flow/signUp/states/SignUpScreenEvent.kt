package com.cafeapp.ui.screens.profile.signUp_flow.signUp.states

sealed class SignUpScreenEvent {

    data class EmailChanged(val email: String) : SignUpScreenEvent()

    data class PasswordChanged(val password: String) : SignUpScreenEvent()

    data class SignUp(val email: String, val password: String) : SignUpScreenEvent()
}
