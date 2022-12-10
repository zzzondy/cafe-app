package com.cafeapp.domain.auth.usecase

import com.cafeapp.domain.auth.manager.UserManager
import com.cafeapp.domain.auth.states.SignInResult
import com.cafeapp.domain.models.User

class SignUpUserUseCase(private val userManager: UserManager) {
    suspend operator fun invoke(email: String, password: String): SignInResult =
        userManager.signUpUser(email, password)
}