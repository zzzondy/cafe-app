package com.cafeapp.data.auth.repository

import com.cafeapp.data.auth.remote.AuthRemoteDataSource
import com.cafeapp.data.auth.remote.states.RemoteCheckUserResult
import com.cafeapp.data.auth.remote.states.RemoteSignInResult
import com.cafeapp.data.auth.remote.states.RemoteSignUpResult
import com.cafeapp.data.auth.util.toDomain
import com.cafeapp.data.auth.util.toDomainUser
import com.cafeapp.domain.auth.repository.AuthRepository
import com.cafeapp.domain.auth.states.*
import com.cafeapp.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    private val userFlow = MutableStateFlow<User?>(null)

    override var currentUser: User?
        get() = authRemoteDataSource.user?.toDomainUser()
        set(value) {
            userFlow.value = value
            userFlow.value
        }

    init {
        currentUser = authRemoteDataSource.user?.toDomainUser()
    }

    override suspend fun signUpUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        photo: ByteArray?
    ): SignUpResult {

        return when (val signUpResult = authRemoteDataSource.signUpUser(
            email,
            password,
            firstName,
            lastName,
            phoneNumber,
            photo
        )) {
            is RemoteSignUpResult.Success -> {
                currentUser = signUpResult.user.toDomainUser()
                SignUpResult.Success(signUpResult.user.toDomainUser())
            }
            is RemoteSignUpResult.NetworkUnavailableError -> SignUpResult.NetworkUnavailableError
            is RemoteSignUpResult.OtherError -> SignUpResult.OtherError
        }
    }

    override suspend fun signInUser(email: String, password: String): SignInResult {
        return when (val signInResult = authRemoteDataSource.signInUser(email, password)) {
            is RemoteSignInResult.Success -> {
                currentUser = signInResult.user.toDomainUser()
                SignInResult.Success(signInResult.user.toDomainUser())
            }
            is RemoteSignInResult.NetworkUnavailableError -> SignInResult.NetworkUnavailableError
            is RemoteSignInResult.WrongCredentialsError -> SignInResult.WrongCredentialsError
            is RemoteSignInResult.OtherError -> SignInResult.OtherError
        }
    }

    override suspend fun signOut() {
        currentUser = null
        authRemoteDataSource.signOut()
    }

    override suspend fun checkUserAlreadyExists(email: String): CheckUserResult {
        return when (authRemoteDataSource.checkUserAlreadyExists(email)) {
            is RemoteCheckUserResult.AlreadyExists -> CheckUserResult.AlreadyExists
            is RemoteCheckUserResult.NotExists -> CheckUserResult.NotExists
            is RemoteCheckUserResult.NetworkUnavailableError -> CheckUserResult.NetworkUnavailableError
            is RemoteCheckUserResult.OtherError -> CheckUserResult.OtherError
        }
    }

    override suspend fun getUserPhoneNumber(userId: String): ObtainingUserPhoneNumberResult =
        authRemoteDataSource.getUserPhoneNumber(userId).toDomain()


    override fun observeCurrentUser(): Flow<User?> =
        userFlow.asStateFlow()
    private fun updateCurrentUser() {
        currentUser = authRemoteDataSource.user?.toDomainUser()
    }

}