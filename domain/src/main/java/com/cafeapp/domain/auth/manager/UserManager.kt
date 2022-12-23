package com.cafeapp.domain.auth.manager

import com.cafeapp.domain.auth.states.SignInResult
import com.cafeapp.domain.auth.states.SignUpResult
import com.cafeapp.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserManager {
    val currentUser: Flow<User?>

    suspend fun signUpUser(email: String, password: String): SignUpResult

    suspend fun signInUser(email: String, password: String): SignInResult

    suspend fun signOut()
}