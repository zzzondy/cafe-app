package com.cafeapp.domain.auth.usecase

import com.cafeapp.domain.auth.manager.UserManager
import com.cafeapp.domain.auth.states.ObtainingUserPhoneNumberResult

class ObtainUserPhoneNumberUseCase(private val userManager: UserManager) {

    suspend operator fun invoke(): ObtainingUserPhoneNumberResult = userManager.getUserPhoneNumber()
}