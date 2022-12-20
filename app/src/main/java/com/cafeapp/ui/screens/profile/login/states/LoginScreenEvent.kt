package com.cafeapp.ui.screens.profile.login.states

sealed class LoginScreenEvent {
    data class SignIn(val email: String, val password: String) : LoginScreenEvent()
}
