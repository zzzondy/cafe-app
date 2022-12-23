package com.cafeapp.domain.auth.manager

import com.cafeapp.domain.auth.repository.AuthRepository
import com.cafeapp.domain.auth.states.SignInResult
import com.cafeapp.domain.auth.states.SignUpResult
import com.cafeapp.domain.models.User
import kotlinx.coroutines.flow.Flow

class UserManagerImpl(private val authRepository: AuthRepository) : UserManager {

    override val currentUser: Flow<User?>
        get() = authRepository.observeCurrentUser()

    override suspend fun signUpUser(email: String, password: String): SignUpResult =
        authRepository.signUpUser(email, password)


    override suspend fun signInUser(email: String, password: String): SignInResult =
        authRepository.signInUser(email, password)

    override suspend fun signOut() {
        authRepository.signOut()
    }
}