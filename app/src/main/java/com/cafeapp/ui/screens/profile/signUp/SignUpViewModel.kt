package com.cafeapp.ui.screens.profile.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeapp.core.providers.dispatchers.DispatchersProvider
import com.cafeapp.domain.auth.states.SignUpResult
import com.cafeapp.domain.auth.states.validation.ValidationEmailResult
import com.cafeapp.domain.auth.states.validation.ValidationPasswordResult
import com.cafeapp.domain.auth.usecase.SignUpUserUseCase
import com.cafeapp.domain.auth.usecase.validation.ValidateEmailUseCase
import com.cafeapp.domain.auth.usecase.validation.ValidatePasswordUseCase
import com.cafeapp.domain.cart.usecase.CreateCartForUserUseCase
import com.cafeapp.ui.screens.profile.signUp.states.SignUpScreenEvent
import com.cafeapp.ui.screens.profile.signUp.states.SignUpScreenState
import com.cafeapp.ui.screens.profile.states.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUserUseCase: SignUpUserUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val createCartForUserUseCase: CreateCartForUserUseCase,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _signUpScreenState =
        MutableStateFlow<SignUpScreenState>(SignUpScreenState.Initially)
    val signUpScreenState: StateFlow<SignUpScreenState> = _signUpScreenState

    private val _loadingState = MutableStateFlow(LoadingState.NotLoading)
    val loadingState: StateFlow<LoadingState> = _loadingState

    private val _validationEmailState =
        MutableStateFlow<ValidationEmailResult>(ValidationEmailResult.Success)
    val validationEmailState: StateFlow<ValidationEmailResult> = _validationEmailState

    private val _validationPasswordState =
        MutableStateFlow<ValidationPasswordResult>(ValidationPasswordResult.Success)
    val validationPasswordState: StateFlow<ValidationPasswordResult> = _validationPasswordState


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
                        when (val result = signUpUserUseCase(event.email, event.password)) {
                            is SignUpResult.Success -> {
                                createCartForUserUseCase(result.user.id)
                                SignUpScreenState.Success
                            }
                            is SignUpResult.NetworkUnavailableError -> SignUpScreenState.NetworkUnavailableError
                            is SignUpResult.UserAlreadyExistsError -> SignUpScreenState.UserAlreadyExistsError
                            is SignUpResult.OtherError -> SignUpScreenState.OtherError
                        }
                    _loadingState.value = LoadingState.NotLoading
                }
            }
        }
    }

}