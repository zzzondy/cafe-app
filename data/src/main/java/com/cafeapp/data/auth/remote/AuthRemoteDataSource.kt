package com.cafeapp.data.auth.remote

import com.cafeapp.data.auth.remote.models.RemoteUser
import com.cafeapp.data.auth.remote.states.RemoteSignInResult

interface AuthRemoteDataSource {
    val user: RemoteUser?

    suspend fun signUpUser(email: String, password: String): RemoteSignInResult

    suspend fun signInUser(email: String, password: String): RemoteSignInResult
}