package com.cafeapp.data.auth.local

import com.cafeapp.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AuthLocalDataSource {
    var currentUser: User?

    fun observeCurrentUser(): Flow<User?>
}