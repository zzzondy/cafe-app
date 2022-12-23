package com.cafeapp.domain.auth.usecase

import com.cafeapp.domain.auth.repository.AuthRepository
import com.cafeapp.domain.auth.states.CheckUserResult

class CheckUserExistsUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String): CheckUserResult =
        authRepository.checkUserAlreadyExists(email = email)
}