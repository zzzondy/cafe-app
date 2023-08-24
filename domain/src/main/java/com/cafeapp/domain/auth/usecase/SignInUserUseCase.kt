package com.cafeapp.domain.auth.usecase

import com.cafeapp.domain.auth.manager.UserManager

class SignInUserUseCase(private val userManager: UserManager) {

    suspend operator fun invoke(email: String, password: String) =
        userManager.signInUser(email, password)
}