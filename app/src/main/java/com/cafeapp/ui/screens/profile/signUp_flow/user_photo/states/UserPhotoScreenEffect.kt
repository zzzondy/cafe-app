package com.cafeapp.ui.screens.profile.signUp_flow.user_photo.states

sealed interface UserPhotoScreenEffect {

    object NavigateBackOnSuccessfulSigning : UserPhotoScreenEffect

    object NavigateBack : UserPhotoScreenEffect

    object ShowLoadingDialog : UserPhotoScreenEffect

    object HideLoadingDialog : UserPhotoScreenEffect
}