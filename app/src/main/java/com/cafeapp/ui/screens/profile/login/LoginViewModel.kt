package com.cafeapp.ui.screens.profile.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeapp.R
import com.cafeapp.core.providers.dispatchers.DispatchersProvider
import com.cafeapp.core.util.UIText
import com.cafeapp.domain.auth.states.SignInResult
import com.cafeapp.domain.auth.usecase.SignInUserUseCase
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

    private val _loginScreenEffect = MutableSharedFlow<LoginScreenEffect>()
    val loginScreenEffect = _loginScreenEffect.asSharedFlow()

    fun onEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.SignIn -> {
                signIn(event.email, event.password)
            }

            is LoginScreenEvent.OnBackButtonClicked -> onBackButtonClicked()
        }
    }

    private fun signIn(email: String, password: String) {
        viewModelScope.launch(dispatchersProvider.io) {
            _loginScreenEffect.emit(LoginScreenEffect.ShowLoadingDialog)

            _loginScreenState.update {
                when (signInUserUseCase(email, password)) {
                    is SignInResult.Success -> {
                        _loginScreenEffect.emit(LoginScreenEffect.NavigateBack)
                        LoginScreenState.Initially
                    }

                    is SignInResult.NetworkUnavailableError -> {
                        LoginScreenState.SomeError(UIText.StringResource(R.string.network_unavailable))
                    }

                    is SignInResult.WrongCredentialsError -> {
                        LoginScreenState.SomeError(UIText.StringResource(R.string.sign_in_error))
                    }

                    is SignInResult.OtherError -> {

                        LoginScreenState.SomeError(UIText.StringResource(R.string.some_error))
                    }
                }
            }

            _loginScreenEffect.emit(LoginScreenEffect.HideLoadingDialog)
        }
    }

    private fun onBackButtonClicked() {
        viewModelScope.launch {
            _loginScreenEffect.emit(LoginScreenEffect.NavigateBack)
        }
    }
}