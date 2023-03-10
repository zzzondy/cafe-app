package com.cafeapp.ui.screens.profile.login.states

sealed interface LoginScreenState {
    object WrongCredentialsError : LoginScreenState

    object NetworkUnavailable : LoginScreenState

    object Initially : LoginScreenState
}
