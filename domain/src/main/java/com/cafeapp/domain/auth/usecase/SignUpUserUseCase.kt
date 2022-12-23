package com.cafeapp.domain.auth.usecase

import com.cafeapp.domain.auth.manager.UserManager
import com.cafeapp.domain.auth.states.SignUpResult

class SignUpUserUseCase(private val userManager: UserManager) {
    suspend operator fun invoke(email: String, password: String): SignUpResult =
        userManager.signUpUser(email, password)
}