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
            is UserDataScreenEvent.FirstNameFieldChanged -> {
                viewModelScope.launch {
                    validationFirstNameResult = validateFirstNameUseCase(event.firstName)
                    _userDataScreenState.update {
                        if (validationFirstNameResult && validationLastNameResult && validationPhoneNumberResult) {
                            UserDataScreenState.EnabledNavigationButton
                        } else {
                            UserDataScreenState.DisabledNavigationButton
                        }
                    }
                }
            }
            is UserDataScreenEvent.LastNameFieldChanged -> {
                viewModelScope.launch {
                    validationLastNameResult = validateLastNameUseCase(event.lastName)
                    _userDataScreenState.update {
                        if (validationFirstNameResult && validationLastNameResult && validationPhoneNumberResult) {
                            UserDataScreenState.EnabledNavigationButton
                        } else {
                            UserDataScreenState.DisabledNavigationButton
                        }
                    }
                }
            }
            is UserDataScreenEvent.PhoneNumberFieldChanged -> {
                viewModelScope.launch {
                    validationPhoneNumberResult =
                        validatePhoneNumberUseCase(event.phoneNumber)
                    _userDataScreenState.update {
                        if (validationFirstNameResult && validationLastNameResult && validationPhoneNumberResult) {
                            UserDataScreenState.EnabledNavigationButton
                        } else {
                            UserDataScreenState.DisabledNavigationButton
                        }
                    }
                }
            }
            UserDataScreenEvent.OnNextButtonClicked -> {
                viewModelScope.launch { _userDataScreenEffect.emit(UserDataScreenEffect.NavigateOnUserPhotoScreen) }
            }
        }
    }
}