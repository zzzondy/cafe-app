package com.cafeapp.domain.auth.usecase.validation

class ValidateFirstNameUseCase {

    operator fun invoke(firstName: String): Boolean = firstName.isNotEmpty()
}