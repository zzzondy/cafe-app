package com.cafeapp.domain.auth.repository

import com.cafeapp.domain.auth.states.Result
import com.cafeapp.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: User?

    suspend fun signUpUser(email: String, password: String): Result<User>

    suspend fun signInUser(email: String, password: String): Result<User>

    fun observeCurrentUser(): Flow<User?>
}