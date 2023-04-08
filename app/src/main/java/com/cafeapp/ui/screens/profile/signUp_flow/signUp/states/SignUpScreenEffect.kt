package com.cafeapp.ui.screens.profile.signUp_flow.signUp.states

sealed class SignUpScreenEffect {
    data class NavigateToDataScreen(val email: String, val password: String) : SignUpScreenEffect()

    object NavigateBack : SignUpScreenEffect()

    object ShowLoadingDialog : SignUpScreenEffect()

    object HideLoadingDialog : SignUpScreenEffect()
}