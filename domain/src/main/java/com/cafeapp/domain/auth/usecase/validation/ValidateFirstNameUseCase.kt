package com.cafeapp.domain.auth.usecase.validation

class ValidateFirstNameUseCase {

    suspend operator fun invoke(firstName: String): Boolean = firstName.isNotEmpty()
}