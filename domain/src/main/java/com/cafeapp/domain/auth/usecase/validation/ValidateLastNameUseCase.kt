package com.cafeapp.domain.auth.usecase.validation

class ValidateLastNameUseCase {

    operator fun invoke(lastName: String): Boolean = lastName.isNotEmpty()
}