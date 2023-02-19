package com.cafeapp.ui.screens.profile.settings.states

sealed class SettingsScreenState {
    object UserAuthenticated : SettingsScreenState()
    object UserNotAuthenticated : SettingsScreenState()
}