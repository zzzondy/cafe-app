package com.cafeapp.data.auth.remote.states

sealed class RemoteCheckUserResult {
    object AlreadyExists : RemoteCheckUserResult()
    object NotExists : RemoteCheckUserResult()
    object NetworkUnavailableError : RemoteCheckUserResult()
    object OtherError : RemoteCheckUserResult()
}
