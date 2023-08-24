package com.cafeapp.ui.screens.profile.signUp_flow.user_photo.states

import com.cafeapp.ui.screens.profile.signUp_flow.models.SignUpParams

sealed class UserPhotoScreenEvent {
    data class SignUp(val signUpParams: SignUpParams) : UserPhotoScreenEvent()

    object OnBackButtonClicked : UserPhotoScreenEvent()
}
