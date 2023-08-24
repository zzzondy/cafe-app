package com.cafeapp.domain.auth.manager

import com.cafeapp.domain.auth.repository.AuthRepository
import com.cafeapp.domain.auth.states.*
import com.cafeapp.domain.models.User
import kotlinx.coroutines.flow.Flow

class UserManagerImpl(
    private val authRepository: AuthRepository
) : UserManager {

    override val currentUser: User?
        get() = authRepository.currentUser

    override suspend fun signUpUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        photo: ByteArray?
    ): SignUpResult =
        authRepository.signUpUser(email, password, firstName, lastName, phoneNumber, photo)

    override suspend fun signInUser(email: String, password: String): SignInResult =
        authRepository.signInUser(email, password)

    override suspend fun signOut() {
        authRepository.signOut()
    }


    override suspend fun getUserPhoneNumber(): ObtainingUserPhoneNumberResult {
        return if (currentUser != null) {
            authRepository.getUserPhoneNumber(currentUser!!.id)
        } else {
            ObtainingUserPhoneNumberResult.UserNotAuthenticatedError
        }
    }

    override suspend fun observeCurrentUser(): Flow<User?> = authRepository.observeCurrentUser()


}