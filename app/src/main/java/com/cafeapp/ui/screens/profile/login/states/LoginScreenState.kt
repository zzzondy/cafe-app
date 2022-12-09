package com.cafeapp.ui.screens.profile.login.states

import com.cafeapp.domain.models.User

sealed class LoginScreenState {
    data class SuccessfullySignIn(val user: User) : LoginScreenState()
    object Failed : LoginScreenState()
    object Initially : LoginScreenState()
}
