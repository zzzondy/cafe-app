package com.cafeapp.data.auth.remote.states

import com.cafeapp.data.auth.remote.models.RemoteUser

sealed class RemoteSignInResult {
    data class Success(val user: RemoteUser): RemoteSignInResult()
    object WrongCredentialsError : RemoteSignInResult()
    object NetworkUnavailableError : RemoteSignInResult()
}
