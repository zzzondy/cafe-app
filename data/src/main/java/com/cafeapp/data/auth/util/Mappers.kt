package com.cafeapp.data.auth.util

import com.cafeapp.data.auth.remote.models.RemoteUser
import com.cafeapp.domain.models.User

fun RemoteUser.toDomainUser(): User {
    return User(id, email, photoUrl, displayName)
}