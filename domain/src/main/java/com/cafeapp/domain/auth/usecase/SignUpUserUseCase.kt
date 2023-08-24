package com.cafeapp.domain.auth.usecase

import com.cafeapp.domain.auth.manager.UserManager
import com.cafeapp.domain.auth.states.SignUpResult

class SignUpUserUseCase(private val userManager: UserManager) {
    suspend operator fun invoke(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        photo: ByteArray?
    ): SignUpResult {
        return when (val result =
            userManager.signUpUser(
                email,
                password,
                firstName,
                lastName,
                "+7$phoneNumber",
                photo
            )) {
            is SignUpResult.NetworkUnavailableError -> SignUpResult.NetworkUnavailableError
            is SignUpResult.OtherError -> SignUpResult.OtherError
            is SignUpResult.Success -> SignUpResult.Success(result.user)
        }
    }
}