package com.cafeapp.ui.screens.profile.signUp_flow.user_photo.states

sealed interface UserPhotoScreenState {
    object Initially : UserPhotoScreenState
    object NetworkUnavailableError : UserPhotoScreenState
    object OtherError : UserPhotoScreenState
}
