package com.cafeapp.ui.screens.profile.settings.main.states

sealed interface SettingsScreenEvent {

    object OnAboutAppClicked : SettingsScreenEvent
    object SignOutClicked : SettingsScreenEvent

    object OnBackButtonClicked : SettingsScreenEvent
}