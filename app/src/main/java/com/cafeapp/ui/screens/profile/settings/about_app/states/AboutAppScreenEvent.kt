package com.cafeapp.ui.screens.profile.settings.about_app.states

sealed interface AboutAppScreenEvent {
    object OnBackButtonClicked : AboutAppScreenEvent
}