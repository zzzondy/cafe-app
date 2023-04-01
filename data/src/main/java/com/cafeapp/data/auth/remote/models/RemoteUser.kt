package com.cafeapp.data.auth.remote.models

data class RemoteUser(
    val id: String,
    val email: String,
    val photoUrl: String?,
    val displayName: String?,
)
