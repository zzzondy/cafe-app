package com.cafeapp.ui.screens.profile.signUp_flow.user_data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeapp.core.providers.dispatchers.DispatchersProvider
import com.cafeapp.domain.auth.usecase.validation.ValidateFirstNameUseCase
import com.cafeapp.domain.auth.usecase.validation.ValidateLastNameUseCase
import com.cafeapp.domain.auth.usecase.validation.ValidatePhoneNumberUseCase
import com.cafeapp.ui.screens.profile.signUp_flow.user_data.states.UserDataScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDataScreenViewModel @Inject constructor(
    private val validateFirstNameUseCase: ValidateFirstNameUseCase,
    private val validateLastNameUseCase: ValidateLastNameUseCase,
    private val validatePhoneNumberUseCase: ValidatePhoneNumberUseCase,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _validationFirstNameResult = MutableStateFlow(false)
    private val _validationLastNameResult = MutableStateFlow(false)
    private val _validationPhoneNumberResult = MutableStateFlow(false)

    val canNavigateTpPhotoScreen: StateFlow<Boolean> = _validationFirstNameResult
        .combine(_validationLastNameResult) { first, second ->
            first && second
        }
        .combine(_validationPhoneNumberResult) { first, second ->
            first && second
        }
        .stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, initialValue = false)

    fun onEvent(event: UserDataScreenEvent) {
        when (event) {
            is UserDataScreenEvent.FirstNameFieldChanged -> {
                viewModelScope.launch {
                    _validationFirstNameResult.value = validateFirstNameUseCase(event.firstName)
                }
            }
            is UserDataScreenEvent.LastNameFieldChanged -> {
                viewModelScope.launch {
                    _validationLastNameResult.value = validateLastNameUseCase(event.lastName)
                }
            }
            is UserDataScreenEvent.PhoneNumberFieldChanged -> {
                viewModelScope.launch {
                    Log.d("TAG", event.phoneNumber)
                    _validationPhoneNumberResult.value =
                        validatePhoneNumberUseCase(event.phoneNumber)
                }
            }
            UserDataScreenEvent.OnNextButtonClicked -> {}

        }
    }
}