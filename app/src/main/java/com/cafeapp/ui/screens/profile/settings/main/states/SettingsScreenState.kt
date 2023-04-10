package com.cafeapp.ui.screens.profile.settings.main.states

sealed class SettingsScreenState {
    object UserAuthenticated : SettingsScreenState()
    object UserNotAuthenticated : SettingsScreenState()
}