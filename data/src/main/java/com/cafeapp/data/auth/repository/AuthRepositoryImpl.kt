package com.cafeapp.data.auth.repository

import com.cafeapp.data.auth.local.AuthLocalDataSource
import com.cafeapp.data.auth.remote.AuthRemoteDataSource
import com.cafeapp.data.auth.remote.states.RemoteResult
import com.cafeapp.data.auth.util.toDomainUser
import com.cafeapp.domain.auth.repository.AuthRepository
import com.cafeapp.domain.auth.states.Result
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

    override suspend fun signUpUser(email: String, password: String): Result<User> {

        return when (val signUpResult = authRemoteDataSource.signUpUser(email, password)) {
            is RemoteResult.Success -> {
                authLocalDataSource.currentUser = signUpResult.data.toDomainUser()
                Result.Success(signUpResult.data.toDomainUser())
            }

            is RemoteResult.Failed -> {
                Result.Failed()
            }
        }
    }

    override suspend fun signInUser(email: String, password: String): Result<User> {
        return when (val signInResult = authRemoteDataSource.signInUser(email, password)) {
            is RemoteResult.Success -> {
                authLocalDataSource.currentUser = signInResult.data.toDomainUser()
                Result.Success(signInResult.data.toDomainUser())
            }
            is RemoteResult.Failed -> {
                Result.Failed()
            }
        }
    }

    override fun observeCurrentUser(): Flow<User?> =
        authLocalDataSource.observeCurrentUser()
}