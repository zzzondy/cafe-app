package com.cafeapp.domain.auth.manager

import com.cafeapp.domain.auth.repository.AuthRepository
import com.cafeapp.domain.auth.states.Result
import com.cafeapp.domain.models.User
import kotlinx.coroutines.flow.Flow

class UserManagerImpl(private val authRepository: AuthRepository) : UserManager {

    override val currentUser: Flow<User?>
        get() = authRepository.observeCurrentUser()

    override suspend fun signUpUser(email: String, password: String): Result<User> =
        authRepository.signUpUser(email, password)


    override suspend fun signInUser(email: String, password: String): Result<User> =
        authRepository.signInUser(email, password)
}