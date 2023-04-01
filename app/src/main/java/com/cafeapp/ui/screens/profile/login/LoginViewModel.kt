package com.cafeapp.ui.screens.profile.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeapp.R
import com.cafeapp.core.providers.dispatchers.DispatchersProvider
import com.cafeapp.core.util.UiText
import com.cafeapp.domain.auth.states.SignInResult
import com.cafeapp.domain.auth.usecase.SignInUserUseCase
import com.cafeapp.ui.common.states.LoadingState
import com.cafeapp.ui.screens.profile.login.states.LoginScreenEffect
import com.cafeapp.ui.screens.profile.login.states.LoginScreenEvent
import com.cafeapp.ui.screens.profile.login.states.LoginScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUserUseCase: SignInUserUseCase,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _loginScreenState = MutableStateFlow<LoginScreenState>(LoginScreenState.Initially)
    val loginScreenState = _loginScreenState.asStateFlow()

    private val _loadingState = MutableStateFlow(LoadingState.NotLoading)
    val loadingState = _loadingState.asStateFlow()

    private val _loginScreenEffect = MutableSharedFlow<LoginScreenEffect>()
    val loginScreenEffect = _loginScreenEffect.asSharedFlow()

    fun onEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.SignIn -> {
                signIn(event.email, event.password)
            }
        }
    }

    private fun signIn(email: String, password: String) {
        viewModelScope.launch(dispatchersProvider.io) {
            _loadingState.update { LoadingState.Loading }

            when (signInUserUseCase(email, password)) {
                is SignInResult.Success -> {
                    _loginScreenState.value = LoginScreenState.Initially
                    _loginScreenEffect.emit(LoginScreenEffect.NavigateBackOnSuccessfullySignIn)
                }

                is SignInResult.NetworkUnavailableError -> {
                    _loginScreenState.value = LoginScreenState.SomeError(UiText.StringResource(R.string.network_unavailable))
                }

                is SignInResult.WrongCredentialsError -> {
                    _loginScreenState.value =
                        LoginScreenState.SomeError(UiText.StringResource(R.string.sign_in_error))
                }

                is SignInResult.OtherError -> {
                    _loginScreenState.value = LoginScreenState.SomeError(UiText.StringResource(R.string.some_error))
                }
            }
            _loadingState.update { LoadingState.NotLoading }
        }
    }
}