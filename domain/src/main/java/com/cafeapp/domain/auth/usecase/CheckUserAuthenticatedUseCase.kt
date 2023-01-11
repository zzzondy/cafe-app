package com.cafeapp.domain.auth.usecase

import com.cafeapp.domain.auth.manager.UserManager

class CheckUserAuthenticatedUseCase(private val userManager: UserManager) {

    operator fun invoke(): Boolean = userManager.currentUser != null

}