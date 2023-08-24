package com.cafeapp.ui.screens.profile.signUp_flow.user_data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeapp.domain.auth.usecase.validation.ValidateFirstNameUseCase
import com.cafeapp.domain.auth.usecase.validation.ValidateLastNameUseCase
import com.cafeapp.domain.auth.usecase.validation.ValidatePhoneNumberUseCase
import com.cafeapp.ui.screens.profile.signUp_flow.user_data.states.UserDataScreenEffect
import com.cafeapp.ui.screens.profile.signUp_flow.user_data.states.UserDataScreenEvent
import com.cafeapp.ui.screens.profile.signUp_flow.user_data.states.UserDataScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDataScreenViewModel @Inject constructor(
    private val validateFirstNameUseCase: ValidateFirstNameUseCase,
    private val validateLastNameUseCase: ValidateLastNameUseCase,
    private val validatePhoneNumberUseCase: ValidatePhoneNumberUseCase
) : ViewModel() {

    private var validationFirstNameResult = false
    private var validationLastNameResult = false
    private var validationPhoneNumberResult = false

    private val _userDataScreenState =
        MutableStateFlow<UserDataScreenState>(UserDataScreenState.DisabledNavigationButton)
    val userDataScreenState = _userDataScreenState.asStateFlow()

    private val _userDataScreenEffect = MutableSharedFlow<UserDataScreenEffect>()
    val userDataScreenEffect = _userDataScreenEffect.asSharedFlow()

    fun onEvent(event: UserDataScreenEvent) {
        when (event) {
            is UserDataScreenEvent.FirstNameFieldChanged -> onFirstNameFieldChanged(event.firstName)

            is UserDataScreenEvent.LastNameFieldChanged -> onLastNameChanged(event.lastName)

            is UserDataScreenEvent.PhoneNumberFieldChanged -> onPhoneNumberChanged(event.phoneNumber)

            is UserDataScreenEvent.OnNextButtonClicked -> onNextButtonClicked()

            is UserDataScreenEvent.OnBackButtonClicked -> onBackButtonClicked()
        }
    }

    private fun onFirstNameFieldChanged(firstName: String) {
        viewModelScope.launch {
            validationFirstNameResult = validateFirstNameUseCase(firstName)
            _userDataScreenState.update {
                if (validationFirstNameResult && validationLastNameResult && validationPhoneNumberResult) {
                    UserDataScreenState.EnabledNavigationButton
                } else {
                    UserDataScreenState.DisabledNavigationButton
                }
            }
        }
    }

    private fun onLastNameChanged(lastName: String) {
        viewModelScope.launch {
            validationLastNameResult = validateLastNameUseCase(lastName)
            _userDataScreenState.update {
                if (validationFirstNameResult && validationLastNameResult && validationPhoneNumberResult) {
                    UserDataScreenState.EnabledNavigationButton
                } else {
                    UserDataScreenState.DisabledNavigationButton
                }
            }
        }
    }

    private fun onPhoneNumberChanged(phoneNumber: String) {
        viewModelScope.launch {
            validationPhoneNumberResult =
                validatePhoneNumberUseCase(phoneNumber)
            _userDataScreenState.update {
                if (validationFirstNameResult && validationLastNameResult && validationPhoneNumberResult) {
                    UserDataScreenState.EnabledNavigationButton
                } else {
                    UserDataScreenState.DisabledNavigationButton
                }
            }
        }
    }

    private fun onNextButtonClicked() {
        viewModelScope.launch { _userDataScreenEffect.emit(UserDataScreenEffect.NavigateOnUserPhotoScreen) }
    }

    private fun onBackButtonClicked() {
        viewModelScope.launch {
            _userDataScreenEffect.emit(UserDataScreenEffect.NavigateBack)
        }
    }
}