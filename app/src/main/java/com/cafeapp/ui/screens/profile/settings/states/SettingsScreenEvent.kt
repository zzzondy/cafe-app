package com.cafeapp.ui.screens.profile.settings.states

sealed interface SettingsScreenEvent {
    object SignOutClicked : SettingsScreenEvent

    object OnBackButtonClicked : SettingsScreenEvent
}