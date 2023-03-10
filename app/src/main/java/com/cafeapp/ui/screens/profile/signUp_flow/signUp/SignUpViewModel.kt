package com.cafeapp.ui.screens.profile.signUp_flow.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeapp.core.providers.dispatchers.DispatchersProvider
import com.cafeapp.domain.auth.states.CheckUserResult
import com.cafeapp.domain.auth.states.validation.ValidationEmailResult
import com.cafeapp.domain.auth.states.validation.ValidationPasswordResult
import com.cafeapp.domain.auth.usecase.CheckUserExistsUseCase
import com.cafeapp.domain.auth.usecase.validation.ValidateEmailUseCase
import com.cafeapp.domain.auth.usecase.validation.ValidatePasswordUseCase
import com.cafeapp.ui.screens.profile.signUp_flow.signUp.states.SignUpScreenEvent
import com.cafeapp.ui.screens.profile.signUp_flow.signUp.states.SignUpScreenState
import com.cafeapp.ui.common.states.LoadingState
import com.cafeapp.ui.screens.profile.signUp_flow.signUp.states.SignUpScreenEffect
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

    private val _loadingState = MutableStateFlow(LoadingState.NotLoading)
    val loadingState: StateFlow<LoadingState> = _loadingState

    private val _validationEmailState =
        MutableStateFlow<ValidationEmailResult>(ValidationEmailResult.Success)
    val validationEmailState = _validationEmailState.asStateFlow()

    private val _validationPasswordState =
        MutableStateFlow<ValidationPasswordResult>(ValidationPasswordResult.Success)
    val validationPasswordState = _validationPasswordState.asStateFlow()


    fun onEvent(event: SignUpScreenEvent) {
        when (event) {
            is SignUpScreenEvent.EmailChanged -> {
                viewModelScope.launch {
                    _validationEmailState.value = validateEmailUseCase(event.email)
                }
            }

            is SignUpScreenEvent.PasswordChanged -> {
                viewModelScope.launch {
                    _validationPasswordState.value = validatePasswordUseCase(event.password)
                }
            }

            is SignUpScreenEvent.SignUp -> {
                viewModelScope.launch(dispatchersProvider.io) {
                    _loadingState.value = LoadingState.Loading
                    _signUpScreenState.value =
                        when (checkUserExistsUseCase(event.email)) {
                            is CheckUserResult.NotExists -> {
                                _screenEffect.emit(SignUpScreenEffect.NavigateToDataScreen(event.email, event.password))
                                SignUpScreenState.Initially
                            }
                            is CheckUserResult.NetworkUnavailableError -> SignUpScreenState.NetworkUnavailableError
                            is CheckUserResult.AlreadyExists -> SignUpScreenState.UserAlreadyExistsError
                            is CheckUserResult.OtherError -> SignUpScreenState.OtherError
                        }
                    _loadingState.value = LoadingState.NotLoading
                }
            }
        }
    }

}