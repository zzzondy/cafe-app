package com.cafeapp.domain.auth.usecase

import com.cafeapp.domain.auth.manager.UserManager

class ObserveCurrentUserUseCase(private val userManager: UserManager) {

    suspend operator fun invoke() = userManager.observeCurrentUser()
}