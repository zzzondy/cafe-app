package com.cafeapp.domain.auth.usecase

import com.cafeapp.domain.auth.manager.UserManager
import com.cafeapp.domain.auth.states.SignInResult
import com.cafeapp.domain.models.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class SignInUserUseCaseTest {

    @Test
    fun `should return wrong credentials error`() = runTest {
        val dummyUserManager = mock<UserManager>()
        val signInUserUseCase = SignInUserUseCase(dummyUserManager)
        val testEmail = "test@test.test"
        val testPassword = "testTest1"

        `when`(
            dummyUserManager.signInUser(
                testEmail,
                testPassword
            )
        ).thenReturn(SignInResult.WrongCredentialsError)

        assertEquals(signInUserUseCase(testEmail, testPassword), SignInResult.WrongCredentialsError)
    }

    @Test
    fun `should return network unavailable error`() = runTest {
        val dummyUserManager = mock<UserManager>()
        val signInUserUseCase = SignInUserUseCase(dummyUserManager)
        val testEmail = "test@test.test"
        val testPassword = "testTest1"

        `when`(
            dummyUserManager.signInUser(
                email = any(),
                password = any()
            )
        ).thenReturn(SignInResult.NetworkUnavailableError)

        assertEquals(
            signInUserUseCase(testEmail, testPassword),
            SignInResult.NetworkUnavailableError
        )
    }

    @Test
    fun `should return other error`() = runTest {
        val dummyUserManager = mock<UserManager>()
        val signInUserUseCase = SignInUserUseCase(dummyUserManager)
        val testEmail = "test@test.test"
        val testPassword = "testTest1"

        `when`(
            dummyUserManager.signInUser(
                email = any(),
                password = any()
            )
        ).thenReturn(SignInResult.OtherError)

        assertEquals(signInUserUseCase(testEmail, testPassword), SignInResult.OtherError)
    }

    @Test
    fun `should return success result`() = runTest {
        val dummyUserManager = mock<UserManager>()
        val signInUserUseCase = SignInUserUseCase(dummyUserManager)
        val testEmail = "test@test.test"
        val testPassword = "testTest1"
        val expectedUser = User(id = "test", email = testEmail, photoUrl = null, displayName = null)

        `when`(
            dummyUserManager.signInUser(
                testEmail,
                testPassword
            )
        ).thenReturn(SignInResult.Success(expectedUser))

        assertEquals(signInUserUseCase(testEmail, testPassword), SignInResult.Success(expectedUser))
    }
}