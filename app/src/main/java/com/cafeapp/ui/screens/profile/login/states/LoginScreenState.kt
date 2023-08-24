package com.cafeapp.ui.screens.profile.login.states

import com.cafeapp.core.util.UIText

sealed class LoginScreenState {
    data class SomeError(val message: UIText) : LoginScreenState()
    object Initially : LoginScreenState()
}
