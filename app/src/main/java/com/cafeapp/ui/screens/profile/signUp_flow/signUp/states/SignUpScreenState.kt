package com.cafeapp.ui.screens.profile.signUp_flow.signUp.states

import com.cafeapp.core.util.UIText

sealed class SignUpScreenState {

    object Initially : SignUpScreenState()

    data class SomeError(val message: UIText) : SignUpScreenState()
}
