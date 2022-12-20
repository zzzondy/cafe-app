package com.cafeapp.domain.auth.usecase

import com.cafeapp.domain.auth.manager.UserManager
import com.cafeapp.domain.models.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class ObserveCurrentUserUseCaseTest {

    @Test
    fun `should return current user`() = runTest {
        val dummyUserManager = mock<UserManager>()
        val observeCurrentUserUseCase = ObserveCurrentUserUseCase(dummyUserManager)

        val flow = flow<User?> { emit(null) }

        `when`(dummyUserManager.currentUser).thenReturn(flow)

        assertEquals(observeCurrentUserUseCase().last(), null)
    }

    @Test
    fun `should return null`() = runTest {
        val dummyUserManager = mock<UserManager>()
        val observeCurrentUserUseCase = ObserveCurrentUserUseCase(dummyUserManager)
        val flow = flow<User?> {
            emit(
                User(
                    id = "test",
                    email = "test@test.test",
                    photoUrl = null,
                    displayName = null
                )
            )
        }

        `when`(dummyUserManager.currentUser).thenReturn(flow)

        assertEquals(
            observeCurrentUserUseCase().last(), User(
                id = "test",
                email = "test@test.test",
                photoUrl = null,
                displayName = null
            )
        )
    }
}