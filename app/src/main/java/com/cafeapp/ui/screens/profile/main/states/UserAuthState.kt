package com.cafeapp.ui.screens.profile.main.states

import com.cafeapp.domain.models.User

sealed class UserAuthState {
    data class Authenticated(val user: User) : UserAuthState()
    object NotAuthenticated : UserAuthState()
}
