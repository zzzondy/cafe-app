package com.cafeapp.ui.screens.profile.login.states

sealed class LoginScreenEvent {
    data class SignIn(val email: String, val password: String) : LoginScreenEvent()
    data class EmailFieldChanged(val email: String) : LoginScreenEvent()
    data class PasswordFieldChanged(val password: String) : LoginScreenEvent()
}
