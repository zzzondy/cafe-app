package com.cafeapp.ui.screens.profile.signUp_flow.user_data.states

sealed interface UserDataScreenState {
    object DisabledNavigationButton : UserDataScreenState
    object EnabledNavigationButton : UserDataScreenState
}