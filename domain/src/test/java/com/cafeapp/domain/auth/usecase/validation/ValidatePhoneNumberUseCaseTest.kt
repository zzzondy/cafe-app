package com.cafeapp.domain.auth.usecase.validation

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ValidatePhoneNumberUseCaseTest {

    @Test
    fun `should return false`() {
        val validatePhoneNumberUseCase = ValidatePhoneNumberUseCase()
        val testPhoneNumber = "2344423"

        assertFalse(validatePhoneNumberUseCase(testPhoneNumber))
    }

    @Test
    fun `should return true`() {
        val validatePhoneNumberUseCase = ValidatePhoneNumberUseCase()
        val testPhoneNumber = "8005553535"

        assertTrue(validatePhoneNumberUseCase(testPhoneNumber))
    }
}