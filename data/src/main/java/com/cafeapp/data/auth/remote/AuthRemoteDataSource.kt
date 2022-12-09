package com.cafeapp.data.auth.remote

import com.cafeapp.data.auth.remote.models.RemoteUser
import com.cafeapp.data.auth.remote.states.RemoteResult

interface AuthRemoteDataSource {
    val user: RemoteUser?

    suspend fun signUpUser(email: String, password: String): RemoteResult<RemoteUser>

    suspend fun signInUser(email: String, password: String): RemoteResult<RemoteUser>
}