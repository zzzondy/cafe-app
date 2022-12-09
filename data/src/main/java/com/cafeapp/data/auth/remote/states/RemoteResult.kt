package com.cafeapp.data.auth.remote.states

sealed class RemoteResult<T> {
    data class Success<T>(val data: T): RemoteResult<T>()
    class Failed<T> : RemoteResult<T>()
}
