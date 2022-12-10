package com.cafeapp.data.auth.repository

import com.cafeapp.data.auth.local.AuthLocalDataSource
import com.cafeapp.data.auth.remote.AuthRemoteDataSource
import com.cafeapp.data.auth.remote.states.RemoteSignInResult
import com.cafeapp.data.auth.util.toDomainUser
import com.cafeapp.domain.auth.repository.AuthRepository
import com.cafeapp.domain.auth.states.SignInResult
import com.cafeapp.domain.models.User
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource
) : AuthRepository {

    init {
        authLocalDataSource.currentUser = authRemoteDataSource.user?.toDomainUser()
    }

    override val currentUser: User?
        get() = authLocalDataSource.currentUser

    override suspend fun signUpUser(email: String, password: String): SignInResult {

        return when (val signUpResult = authRemoteDataSource.signUpUser(email, password)) {
            is RemoteSignInResult.Success -> {
                authLocalDataSource.currentUser = signUpResult.user.toDomainUser()
                SignInResult.Success(signUpResult.user.toDomainUser())
            }

            is RemoteSignInResult.NetworkUnavailableError -> {
                SignInResult.NetworkUnavailableError
            }

            is RemoteSignInResult.WrongCredentialsError -> {
                SignInResult.WrongCredentialsError
            }
        }
    }

    override suspend fun signInUser(email: String, password: String): SignInResult {
        return when (val signInResult = authRemoteDataSource.signInUser(email, password)) {
            is RemoteSignInResult.Success -> {
                authLocalDataSource.currentUser = signInResult.user.toDomainUser()
                SignInResult.Success(signInResult.user.toDomainUser())
            }
            is RemoteSignInResult.NetworkUnavailableError -> {
                SignInResult.NetworkUnavailableError
            }
            is RemoteSignInResult.WrongCredentialsError -> {
                SignInResult.WrongCredentialsError
            }
        }
    }

    override fun observeCurrentUser(): Flow<User?> =
        authLocalDataSource.observeCurrentUser()
}