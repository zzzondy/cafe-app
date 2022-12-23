package com.cafeapp.ui.screens.profile.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeapp.core.providers.dispatchers.DispatchersProvider
import com.cafeapp.domain.auth.usecase.ObserveCurrentUserUseCase
import com.cafeapp.domain.auth.usecase.SignOutUserUseCase
import com.cafeapp.ui.screens.profile.main.states.ProfileEvent
import com.cafeapp.ui.screens.profile.main.states.UserAuthState
import com.cafeapp.ui.screens.profile.states.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val observeCurrentUserUseCase: ObserveCurrentUserUseCase,
    private val signOutUserUseCase: SignOutUserUseCase,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _userAuthState = MutableStateFlow<UserAuthState>(UserAuthState.NotAuthenticated)
    val userAuthState: StateFlow<UserAuthState> = _userAuthState

    private val _loadingState = MutableStateFlow(LoadingState.NotLoading)
    val loadingState: StateFlow<LoadingState> = _loadingState

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

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.SignOutClicked -> {
                viewModelScope.launch(dispatchersProvider.io) {
                    _loadingState.value = LoadingState.Loading
                    signOutUserUseCase()
                    _loadingState.value = LoadingState.NotLoading
                }
            }
        }
    }
}