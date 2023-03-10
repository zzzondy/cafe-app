package com.cafeapp.ui.screens.profile.login.states

sealed interface LoginScreenEffect {
    object NavigateBackOnSuccessfullySignIn : LoginScreenEffect
}
