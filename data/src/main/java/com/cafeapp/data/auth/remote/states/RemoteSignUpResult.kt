package com.cafeapp.data.auth.remote.states

import com.cafeapp.data.auth.remote.models.RemoteUser

sealed class RemoteSignUpResult {
    data class Success(val user: RemoteUser) : RemoteSignUpResult()
    object UserAlreadyExistsError : RemoteSignUpResult()
    object NetworkUnavailableError : RemoteSignUpResult()
    object OtherError : RemoteSignUpResult()
}
