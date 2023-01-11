package com.cafeapp.data.auth.remote

import com.cafeapp.data.auth.remote.models.RemoteUser
import com.cafeapp.data.auth.remote.states.RemoteCheckUserResult
import com.cafeapp.data.auth.remote.states.RemoteSignInResult
import com.cafeapp.data.auth.remote.states.RemoteSignUpResult

interface AuthRemoteDataSource {
    var user: RemoteUser?

    suspend fun signUpUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        photo: ByteArray?
    ): RemoteSignUpResult

    suspend fun signInUser(email: String, password: String): RemoteSignInResult

    suspend fun checkUserAlreadyExists(email: String): RemoteCheckUserResult

    suspend fun signOut()
}