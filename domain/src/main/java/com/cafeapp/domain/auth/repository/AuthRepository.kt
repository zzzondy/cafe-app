package com.cafeapp.domain.auth.repository

import com.cafeapp.domain.auth.states.CheckUserResult
import com.cafeapp.domain.auth.states.SignInResult
import com.cafeapp.domain.auth.states.SignUpResult
import com.cafeapp.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    var currentUser: User?

    suspend fun signUpUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        photo: ByteArray?
    ): SignUpResult

    suspend fun signInUser(email: String, password: String): SignInResult
    suspend fun signOut()
    suspend fun checkUserAlreadyExists(email: String): CheckUserResult
    fun observeCurrentUser(): Flow<User?>

    fun updateCurrentUser()
}