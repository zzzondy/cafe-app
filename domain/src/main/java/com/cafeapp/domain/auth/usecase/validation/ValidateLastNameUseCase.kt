package com.cafeapp.domain.auth.usecase.validation

class ValidateLastNameUseCase {

    suspend operator fun invoke(lastName: String): Boolean = lastName.isNotEmpty()
}