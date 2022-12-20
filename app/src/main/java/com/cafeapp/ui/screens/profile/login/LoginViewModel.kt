package com.cafeapp.ui.screens.profile.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeapp.core.providers.dispatchers.DispatchersProvider
import com.cafeapp.domain.auth.states.SignInResult
import com.cafeapp.domain.auth.usecase.SignInUserUseCase
import com.cafeapp.ui.screens.profile.states.LoadingState
import com.cafeapp.ui.screens.profile.login.states.LoginScreenEvent
import com.cafeapp.ui.screens.profile.login.states.LoginScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUserUseCase: SignInUserUseCase,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _loginScreenState = MutableStateFlow<LoginScreenState>(LoginScreenState.Initially)
    val loginScreenState: StateFlow<LoginScreenState> = _loginScreenState

    private val _loadingState = MutableStateFlow(LoadingState.NotLoading)
    val loadingState: StateFlow<LoadingState> = _loadingState

    fun onEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.SignIn -> {
                signIn(event.email, event.password)
            }
        }
    }

    private fun signIn(email: String, password: String) {
        viewModelScope.launch(dispatchersProvider.io) {
            _loadingState.value = LoadingState.Loading
            when (signInUserUseCase(email, password)) {
                is SignInResult.Success -> {
                    _loginScreenState.value =
                        LoginScreenState.SuccessfullySignIn
                }

                is SignInResult.NetworkUnavailableError -> {
                    _loginScreenState.value = LoginScreenState.NetworkUnavailable
                }

                is SignInResult.WrongCredentialsError -> {
                    _loginScreenState.value = LoginScreenState.WrongCredentialsError
                }
                is SignInResult.OtherError -> {
                    _loginScreenState.value = LoginScreenState.NetworkUnavailable
                }
            }
            _loadingState.value = LoadingState.NotLoading
        }
    }
}