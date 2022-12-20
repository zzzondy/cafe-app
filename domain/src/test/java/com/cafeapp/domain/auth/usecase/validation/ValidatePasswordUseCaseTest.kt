package com.cafeapp.domain.auth.usecase.validation

import com.cafeapp.domain.auth.states.validation.ValidationPasswordResult
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ValidatePasswordUseCaseTest {
    private val validatePasswordUseCase = ValidatePasswordUseCase()

    @Test
    fun `should return password is result`() {
        assertEquals(validatePasswordUseCase("sdfd"), ValidationPasswordResult.VeryShortPassword)
    }

    @Test
    fun `should return not contains letters result`() {
        assertEquals(validatePasswordUseCase("123456789"), ValidationPasswordResult.NotContainsLettersOrDigits)
    }

    @Test
    fun `should return not contains digits result`() {
        assertEquals(validatePasswordUseCase("qwertyuio"), ValidationPasswordResult.NotContainsLettersOrDigits)
    }

    @Test
    fun `should return not contains lower case letters result`() {
        assertEquals(validatePasswordUseCase("QWERTYUIOP1"), ValidationPasswordResult.NotContainsLowerOrUpperCase)
    }

    @Test
    fun `should return not contains upper case letters result`() {
        assertEquals(validatePasswordUseCase("qwertyuiop1"), ValidationPasswordResult.NotContainsLowerOrUpperCase)
    }

    @Test
    fun `should return successful result`() {
        assertEquals(validatePasswordUseCase("qwerTYUIOp1"), ValidationPasswordResult.Success)
    }
}