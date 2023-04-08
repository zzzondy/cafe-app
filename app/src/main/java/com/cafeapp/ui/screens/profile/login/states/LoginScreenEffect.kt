package com.cafeapp.ui.screens.profile.login.states

sealed interface LoginScreenEffect {

    object NavigateBack : LoginScreenEffect

    object ShowLoadingDialog : LoginScreenEffect

    object HideLoadingDialog : LoginScreenEffect
}
