package com.cafeapp.domain.models


data class User(
    val id: String,
    val email: String,
    val photoUrl: String?,
    val displayName: String?,
)
