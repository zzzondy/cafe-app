package com.cafeapp.ui.screens.profile.signUp_flow.user_photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeapp.R
import com.cafeapp.core.providers.dispatchers.DispatchersProvider
import com.cafeapp.core.util.UIText
import com.cafeapp.domain.auth.states.SignUpResult
import com.cafeapp.domain.auth.usecase.SignUpUserUseCase
import com.cafeapp.ui.screens.profile.signUp_flow.models.SignUpParams
import com.cafeapp.ui.screens.profile.signUp_flow.user_photo.states.UserPhotoScreenEffect
import com.cafeapp.ui.screens.profile.signUp_flow.user_photo.states.UserPhotoScreenEvent
import com.cafeapp.ui.screens.profile.signUp_flow.user_photo.states.UserPhotoScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPhotoScreenViewModel @Inject constructor(
    private val signUpUserUseCase: SignUpUserUseCase,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _userPhotoScreenState =
        MutableStateFlow<UserPhotoScreenState>(UserPhotoScreenState.Initially)
    val userPhotoScreenState = _userPhotoScreenState.asStateFlow()

    private val _userPhotoScreenEffect = MutableSharedFlow<UserPhotoScreenEffect>()
    val userPhotoScreenEffect = _userPhotoScreenEffect.asSharedFlow()

    fun onEvent(event: UserPhotoScreenEvent) {
        when (event) {
            is UserPhotoScreenEvent.SignUp -> onSignUp(event.signUpParams)

            is UserPhotoScreenEvent.OnBackButtonClicked -> onBackButtonClicked()
        }
    }

    private fun onSignUp(params: SignUpParams) {
        viewModelScope.launch(dispatchersProvider.io) {
            _userPhotoScreenEffect.emit(UserPhotoScreenEffect.ShowLoadingDialog)

            val signUpResult = signUpUserUseCase(
                email = params.email!!,
                password = params.password!!,
                firstName = params.firstName!!,
                lastName = params.lastName!!,
                phoneNumber = params.phoneNumber!!,
                photo = params.photoUri
            )

            when (signUpResult) {
                is SignUpResult.Success ->
                    _userPhotoScreenEffect.emit(UserPhotoScreenEffect.NavigateBackOnSuccessfulSigning)

                is SignUpResult.NetworkUnavailableError ->
                    _userPhotoScreenState.update {
                        UserPhotoScreenState.SomeError(UIText.StringResource(R.string.network_unavailable))
                    }

                is SignUpResult.OtherError ->
                    _userPhotoScreenState.update {
                        UserPhotoScreenState.SomeError(UIText.StringResource(R.string.some_error))
                    }
            }

            _userPhotoScreenEffect.emit(UserPhotoScreenEffect.HideLoadingDialog)
        }
    }

    private fun onBackButtonClicked() {
        viewModelScope.launch {
            _userPhotoScreenEffect.emit(UserPhotoScreenEffect.NavigateBack)
        }
    }
}