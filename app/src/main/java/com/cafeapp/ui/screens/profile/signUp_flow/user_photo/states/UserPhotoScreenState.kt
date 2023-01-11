package com.cafeapp.ui.screens.profile.signUp_flow.user_photo.states

import com.cafeapp.domain.models.User

sealed class UserPhotoScreenState {
    object Initially : UserPhotoScreenState()
    data class Success(val user: User) : UserPhotoScreenState()
    object NetworkUnavailableError : UserPhotoScreenState()
    object OtherError : UserPhotoScreenState()
}
