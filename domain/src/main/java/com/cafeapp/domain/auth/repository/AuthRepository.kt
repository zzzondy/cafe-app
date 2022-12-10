package com.cafeapp.domain.auth.repository

import com.cafeapp.domain.auth.states.SignInResult
import com.cafeapp.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: User?

    suspend fun signUpUser(email: String, password: String): SignInResult

    suspend fun signInUser(email: String, password: String): SignInResult

    fun observeCurrentUser(): Flow<User?>
}