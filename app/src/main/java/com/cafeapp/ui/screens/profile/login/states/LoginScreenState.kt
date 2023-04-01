package com.cafeapp.ui.screens.profile.login.states

import com.cafeapp.core.util.UiText

sealed class LoginScreenState {
    data class SomeError(val message: UiText) : LoginScreenState()
    object Initially : LoginScreenState()
}
