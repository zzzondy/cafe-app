package com.cafeapp.ui.screens.profile.settings.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeapp.core.providers.dispatchers.DispatchersProvider
import com.cafeapp.domain.auth.usecase.CheckUserAuthenticatedUseCase
import com.cafeapp.domain.auth.usecase.SignOutUserUseCase
import com.cafeapp.ui.screens.profile.settings.main.states.SettingsScreenEffect
import com.cafeapp.ui.screens.profile.settings.main.states.SettingsScreenEvent
import com.cafeapp.ui.screens.profile.settings.main.states.SettingsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val signOutUserUseCase: SignOutUserUseCase,
    private val checkUserAuthenticatedUseCase: CheckUserAuthenticatedUseCase,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _settingsScreenState =
        MutableStateFlow<SettingsScreenState>(SettingsScreenState.UserNotAuthenticated)
    val settingsScreenState = _settingsScreenState.asStateFlow()

    private val _screenEffect = MutableSharedFlow<SettingsScreenEffect>()
    val screenEffect = _screenEffect.asSharedFlow()

    init {
        screenEntered()
    }

    fun onEvent(event: SettingsScreenEvent) {
        when (event) {
            SettingsScreenEvent.SignOutClicked -> signOutEvent()

            SettingsScreenEvent.OnBackButtonClicked -> onBackButtonClicked()

            SettingsScreenEvent.OnAboutAppClicked -> onAboutAppClicked()
        }
    }

    private fun signOutEvent() {
        viewModelScope.launch(dispatchersProvider.io) {
            signOutUserUseCase()
        }
    }

    private fun screenEntered() {
        viewModelScope.launch(dispatchersProvider.io) {
            _settingsScreenState.value =
                if (checkUserAuthenticatedUseCase()) SettingsScreenState.UserAuthenticated else SettingsScreenState.UserNotAuthenticated
        }
    }

    private fun onAboutAppClicked() {
        viewModelScope.launch {
            _screenEffect.emit(SettingsScreenEffect.NavigateToAboutAppScreen)
        }
    }

    private fun onBackButtonClicked() {
        viewModelScope.launch {
            _screenEffect.emit(SettingsScreenEffect.NavigateBack)
        }
    }
}