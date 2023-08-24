package com.cafeapp.ui.screens.profile.main.states

sealed interface ProfileScreenEffect {
    object NavigateToOrdersList : ProfileScreenEffect

    object NavigateToSettings : ProfileScreenEffect
}