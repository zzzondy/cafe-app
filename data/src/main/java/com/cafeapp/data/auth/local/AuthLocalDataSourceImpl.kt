package com.cafeapp.data.auth.local

import com.cafeapp.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AuthLocalDataSourceImpl : AuthLocalDataSource {
    private val userFlow: MutableStateFlow<User?> = MutableStateFlow(null)

    override var currentUser: User?
        get() = userFlow.value
        set(value) {
            userFlow.value = value
        }

    override fun observeCurrentUser(): Flow<User?> = userFlow
}