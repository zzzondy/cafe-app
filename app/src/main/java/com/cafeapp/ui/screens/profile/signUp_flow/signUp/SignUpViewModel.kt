package com.cafeapp.ui.screens.profile.signUp_flow.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeapp.R
import com.cafeapp.core.providers.dispatchers.DispatchersProvider
import com.cafeapp.core.util.UIText
import com.cafeapp.domain.auth.states.CheckUserResult
import com.cafeapp.domain.auth.states.validation.ValidationEmailResult
import com.cafeapp.domain.auth.states.validation.ValidationPasswordResult
import com.cafeapp.domain.auth.usecase.CheckUserExistsUseCase
import com.cafeapp.domain.auth.usecase.validation.ValidateEmailUseCase
import com.cafeapp.domain.auth.usecase.validation.ValidatePasswordUseCase
import com.cafeapp.ui.screens.profile.signUp_flow.signUp.states.SignUpScreenEffect
import com.cafeapp.ui.screens.profile.signUp_flow.signUp.states.SignUpScreenEvent
import com.cafeapp.ui.screens.profile.signUp_flow.signUp.states.SignUpScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val checkUserExistsUseCase: CheckUserExistsUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _signUpScreenState =
        MutableStateFlow<SignUpScreenState>(SignUpScreenState.Initially)
    val signUpScreenState = _signUpScreenState.asStateFlow()

    private val _screenEffect = MutableSharedFlow<SignUpScreenEffect>()
    val screenEffect = _screenEffect.asSharedFlow()

    private val _validationEmailState =
        MutableStateFlow<ValidationEmailResult>(ValidationEmailResult.Success)
    val validationEmailState = _validationEmailState.asStateFlow()

    private val _validationPasswordState =
        MutableStateFlow<ValidationPasswordResult>(ValidationPasswordResult.Success)
    val validationPasswordState = _validationPasswordState.asStateFlow()


    fun onEvent(event: SignUpScreenEvent) {
        when (event) {
            is SignUpScreenEvent.EmailChanged -> onEmailChanged(event.email)

            is SignUpScreenEvent.PasswordChanged -> onPasswordChanged(event.password)

            is SignUpScreenEvent.SignUp -> onSignUpClicked(event.email, event.password)

            is SignUpScreenEvent.OnBackButtonClicked -> onBackButtonClicked()
        }
    }

    private fun onEmailChanged(email: String) {
        viewModelScope.launch {
            _validationEmailState.update { validateEmailUseCase(email) }
        }
    }

    private fun onPasswordChanged(password: String) {
        viewModelScope.launch {
            _validationPasswordState.update { validatePasswordUseCase(password) }
        }
    }

    private fun onSignUpClicked(email: String, password: String) {
        viewModelScope.launch(dispatchersProvider.io) {
            _screenEffect.emit(SignUpScreenEffect.ShowLoadingDialog)
            _signUpScreenState.update {
                when (checkUserExistsUseCase(email)) {
                    is CheckUserResult.NotExists -> {
                        _screenEffect.emit(SignUpScreenEffect.NavigateToDataScreen(email, password))
                        SignUpScreenState.Initially
                    }
                    is CheckUserResult.NetworkUnavailableError -> SignUpScreenState.SomeError(
                        UIText.StringResource(R.string.network_unavailable)
                    )
                    is CheckUserResult.AlreadyExists -> SignUpScreenState.SomeError(
                        UIText.StringResource(R.string.user_already_exists)
                    )
                    is CheckUserResult.OtherError -> SignUpScreenState.SomeError(
                        UIText.StringResource(R.string.some_error)
                    )
                }
            }
            _screenEffect.emit(SignUpScreenEffect.HideLoadingDialog)
        }
    }

    private fun onBackButtonClicked() {
        viewModelScope.launch {
            _screenEffect.emit(SignUpScreenEffect.NavigateBack)
        }
    }
}