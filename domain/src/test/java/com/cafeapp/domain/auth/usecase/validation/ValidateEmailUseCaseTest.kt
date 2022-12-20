package com.cafeapp.domain.auth.usecase.validation

import com.cafeapp.domain.auth.states.validation.ValidationEmailResult
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ValidateEmailUseCaseTest {
    val validateEmailUseCase = ValidateEmailUseCase()

    @Test
    fun `should return email is empty`() {
        assertEquals(validateEmailUseCase(""), ValidationEmailResult.EmailIsEmpty)
    }

    @Test
    fun `should return email is not valid`() {
        assertEquals(validateEmailUseCase("sdfsf@"), ValidationEmailResult.NotValidEmail)
    }

    @Test
    fun `should return email is valid`() {
        assertEquals(validateEmailUseCase("artemr19032006@gmail.com"), ValidationEmailResult.Success)
    }
}