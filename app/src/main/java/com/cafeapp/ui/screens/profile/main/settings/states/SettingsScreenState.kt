package com.cafeapp.ui.screens.profile.main.settings.states

sealed class SettingsScreenState {
    object UserAuthenticated : SettingsScreenState()
    object UserNotAuthenticated : SettingsScreenState()
}