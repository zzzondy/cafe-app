package com.cafeapp.domain.auth.states

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    class Failed<T> : Result<T>()
}
