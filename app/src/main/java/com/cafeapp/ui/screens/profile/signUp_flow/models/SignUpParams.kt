package com.cafeapp.ui.screens.profile.signUp_flow.models


data class SignUpParams(
    val email: String? = null,
    val password: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val phoneNumber: String? = null,
    val photoUri: ByteArray? = null,
)
