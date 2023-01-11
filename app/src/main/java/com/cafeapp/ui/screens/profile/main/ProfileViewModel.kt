package com.cafeapp.ui.screens.profile.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeapp.core.providers.dispatchers.DispatchersProvider
import com.cafeapp.domain.auth.usecase.ObserveCurrentUserUseCase
import com.cafeapp.domain.auth.usecase.SignOutUserUseCase
import com.cafeapp.ui.screens.profile.main.states.UserAuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val observeCurrentUserUseCase: ObserveCurrentUserUseCase,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _userAuthState = MutableStateFlow<UserAuthState>(UserAuthState.NotAuthenticated)
    val userAuthState: StateFlow<UserAuthState> = _userAuthState

    init {
        viewModelScope.launch(dispatchersProvider.io) {
            observeCurrentUserUseCase()
                .onEach { user ->
                    _userAuthState.value = if (user != null)
                        UserAuthState.Authenticated(user) else UserAuthState.NotAuthenticated
                }
                .flowOn(dispatchersProvider.io)
                .collect()
        }
    }
}