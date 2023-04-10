package com.cafeapp.ui.screens.profile.settings.main.states

sealed interface SettingsScreenEffect {

    object NavigateToAboutAppScreen : SettingsScreenEffect
    object NavigateBack : SettingsScreenEffect
}