package com.cafeapp.ui.screens.profile.main.settings.states

sealed class SettingsScreenEvent {
    object SignOutClicked : SettingsScreenEvent()
}