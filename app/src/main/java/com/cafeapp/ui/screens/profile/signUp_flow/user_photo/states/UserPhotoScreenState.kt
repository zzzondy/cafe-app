package com.cafeapp.ui.screens.profile.signUp_flow.user_photo.states

import com.cafeapp.core.util.UIText

sealed class UserPhotoScreenState {
    data class SomeError(val message: UIText) : UserPhotoScreenState()
    object Initially : UserPhotoScreenState()
}
