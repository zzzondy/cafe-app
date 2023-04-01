package com.cafeapp.domain.auth.repository

import com.cafeapp.domain.auth.states.*
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

    suspend fun getUserPhoneNumber(userId: String): ObtainingUserPhoneNumberResult

    fun observeCurrentUser(): Flow<User?>
}