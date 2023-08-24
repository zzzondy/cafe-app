package com.cafeapp.ui.screens.profile.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeapp.core.providers.dispatchers.DispatchersProvider
import com.cafeapp.domain.auth.usecase.ObserveCurrentUserUseCase
import com.cafeapp.ui.screens.profile.main.states.ProfileScreenEffect
import com.cafeapp.ui.screens.profile.main.states.ProfileScreenEvent
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

    private val _screenEffect = MutableSharedFlow<ProfileScreenEffect>()
    val screenEffect = _screenEffect.asSharedFlow()

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


    fun onEvent(event: ProfileScreenEvent) {
        when (event) {
            ProfileScreenEvent.OnOrdersListClicked -> onOrdersListClicked()

            ProfileScreenEvent.OnSettingClicked -> onSettingsClicked()
        }
    }

    private fun onOrdersListClicked() {
        viewModelScope.launch {
            _screenEffect.emit(ProfileScreenEffect.NavigateToOrdersList)
        }
    }

    private fun onSettingsClicked() {
        viewModelScope.launch {
            _screenEffect.emit(ProfileScreenEffect.NavigateToSettings)
        }
    }
}