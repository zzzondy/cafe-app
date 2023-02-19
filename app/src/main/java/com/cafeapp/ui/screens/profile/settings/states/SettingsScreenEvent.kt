package com.cafeapp.ui.screens.profile.settings.states

sealed class SettingsScreenEvent {
    object SignOutClicked : SettingsScreenEvent()
}