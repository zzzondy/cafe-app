package com.cafeapp.domain.auth.usecase

import com.cafeapp.domain.auth.manager.UserManager
import com.cafeapp.domain.auth.states.SignUpResult
import com.cafeapp.domain.models.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class SignUpUserUseCaseTest {

    @Test
    fun `should return user already exists error`() = runTest {
        val dummyUserManager = mock<UserManager>()
        val signUpUserUseCase = SignUpUserUseCase(dummyUserManager)
        val testEmail = "test@test.test"
        val testPassword = "testTest1"

        `when`(
            dummyUserManager.signUpUser(
                testEmail,
                testPassword
            )
        ).thenReturn(SignUpResult.UserAlreadyExistsError)

        assertEquals(
            signUpUserUseCase(testEmail, testPassword),
            SignUpResult.UserAlreadyExistsError
        )
    }

    @Test
    fun `should return network unavailable error`() = runTest {
        val dummyUserManager = mock<UserManager>()
        val signUpUserUseCase = SignUpUserUseCase(dummyUserManager)
        val testEmail = "test@test.test"
        val testPassword = "testTest1"

        `when`(
            dummyUserManager.signUpUser(
                any(),
                any()
            )
        ).thenReturn(SignUpResult.NetworkUnavailableError)

        assertEquals(
            signUpUserUseCase(testEmail, testPassword),
            SignUpResult.NetworkUnavailableError
        )
    }

    @Test
    fun `should return other error`() = runTest {
        val dummyUserManager = mock<UserManager>()
        val signUpUserUseCase = SignUpUserUseCase(dummyUserManager)
        val testEmail = "test@test.test"
        val testPassword = "testTest1"

        `when`(
            dummyUserManager.signUpUser(
                any(),
                any()
            )
        ).thenReturn(SignUpResult.OtherError)

        assertEquals(signUpUserUseCase(testEmail, testPassword), SignUpResult.OtherError)
    }

    @Test
    fun `should return successful result`() = runTest {
        val dummyUserManager = mock<UserManager>()
        val signUpUserUseCase = SignUpUserUseCase(dummyUserManager)
        val testEmail = "test@test.test"
        val testPassword = "testTest1"
        val expectedUser = User(id = "test", email = testEmail, photoUrl = null, displayName = null)

        `when`(
            dummyUserManager.signUpUser(
                testEmail,
                testPassword
            )
        ).thenReturn(SignUpResult.Success(expectedUser))

        assertEquals(
            signUpUserUseCase(testEmail, testPassword),
            SignUpResult.Success(expectedUser)
        )
    }
}