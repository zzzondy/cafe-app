package com.cafeapp.data.auth.repository

import com.cafeapp.data.auth.local.AuthLocalDataSource
import com.cafeapp.data.auth.remote.AuthRemoteDataSource
import com.cafeapp.data.auth.remote.states.RemoteCheckUserResult
import com.cafeapp.data.auth.remote.states.RemoteSignInResult
import com.cafeapp.data.auth.remote.states.RemoteSignUpResult
import com.cafeapp.data.auth.util.toDomainUser
import com.cafeapp.domain.auth.repository.AuthRepository
import com.cafeapp.domain.auth.states.CheckUserResult
import com.cafeapp.domain.auth.states.SignInResult
import com.cafeapp.domain.auth.states.SignUpResult
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

    override suspend fun signUpUser(email: String, password: String): SignUpResult {

        return when (val signUpResult = authRemoteDataSource.signUpUser(email, password)) {
            is RemoteSignUpResult.Success -> {
                authLocalDataSource.currentUser = signUpResult.user.toDomainUser()
                SignUpResult.Success(signUpResult.user.toDomainUser())
            }
            is RemoteSignUpResult.UserAlreadyExistsError -> SignUpResult.UserAlreadyExistsError
            is RemoteSignUpResult.NetworkUnavailableError -> SignUpResult.NetworkUnavailableError
            is RemoteSignUpResult.OtherError -> SignUpResult.OtherError
        }
    }

    override suspend fun signInUser(email: String, password: String): SignInResult {
        return when (val signInResult = authRemoteDataSource.signInUser(email, password)) {
            is RemoteSignInResult.Success -> {
                authLocalDataSource.currentUser = signInResult.user.toDomainUser()
                SignInResult.Success(signInResult.user.toDomainUser())
            }
            is RemoteSignInResult.NetworkUnavailableError -> SignInResult.NetworkUnavailableError
            is RemoteSignInResult.WrongCredentialsError -> SignInResult.WrongCredentialsError
            is RemoteSignInResult.OtherError -> SignInResult.OtherError
        }
    }

    override suspend fun signOut() {
        authLocalDataSource.currentUser = null
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

    override fun observeCurrentUser(): Flow<User?> =
        authLocalDataSource.observeCurrentUser()
}