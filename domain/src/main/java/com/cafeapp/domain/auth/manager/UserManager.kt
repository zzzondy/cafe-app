package com.cafeapp.domain.auth.manager

import com.cafeapp.domain.auth.states.Result
import com.cafeapp.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserManager {
    val currentUser: Flow<User?>

    suspend fun signUpUser(email: String, password: String): Result<User>

    suspend fun signInUser(email: String, password: String): Result<User>
}