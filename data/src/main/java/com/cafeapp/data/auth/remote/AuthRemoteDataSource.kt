package com.cafeapp.data.auth.remote

import com.cafeapp.data.auth.remote.models.RemoteUser
import com.cafeapp.data.auth.remote.states.RemoteSignInResult
import com.cafeapp.data.auth.remote.states.RemoteSignUpResult

interface AuthRemoteDataSource {
    val user: RemoteUser?

    suspend fun signUpUser(email: String, password: String): RemoteSignUpResult

    suspend fun signInUser(email: String, password: String): RemoteSignInResult
}