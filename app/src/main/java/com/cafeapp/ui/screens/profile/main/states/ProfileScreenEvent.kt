package com.cafeapp.ui.screens.profile.main.states

sealed interface ProfileScreenEvent {
    object OnOrdersListClicked : ProfileScreenEvent

    object OnSettingClicked : ProfileScreenEvent
}