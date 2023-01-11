package com.cafeapp.ui.screens.profile.signUp_flow.user_photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeapp.core.providers.dispatchers.DispatchersProvider
import com.cafeapp.domain.auth.states.SignUpResult
import com.cafeapp.domain.auth.usecase.SignUpUserUseCase
import com.cafeapp.ui.screens.profile.signUp_flow.user_photo.states.UserPhotoScreenEvent
import com.cafeapp.ui.screens.profile.signUp_flow.user_photo.states.UserPhotoScreenState
import com.cafeapp.ui.screens.profile.states.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPhotoScreenViewModel @Inject constructor(
    private val signUpUserUseCase: SignUpUserUseCase,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _loadingState = MutableStateFlow(LoadingState.NotLoading)
    val loadingState = _loadingState.asStateFlow()

    private val _userPhotoScreenState =
        MutableStateFlow<UserPhotoScreenState>(UserPhotoScreenState.Initially)
    val userPhotoScreenState = _userPhotoScreenState.asStateFlow()

    fun onEvent(event: UserPhotoScreenEvent) {
        when (event) {
            is UserPhotoScreenEvent.SignUp -> {
                viewModelScope.launch(dispatchersProvider.io) {
                    _loadingState.value = LoadingState.Loading
                    val params = event.signUpParams
                    val signUpResult = signUpUserUseCase(
                        email = params.email!!,
                        password = params.password!!,
                        firstName = params.firstName!!,
                        lastName = params.lastName!!,
                        phoneNumber = params.phoneNumber!!,
                        photo = params.photoUri
                    )
                    _userPhotoScreenState.value = when (signUpResult) {
                        is SignUpResult.Success -> UserPhotoScreenState.Success(signUpResult.user)
                        is SignUpResult.NetworkUnavailableError -> UserPhotoScreenState.NetworkUnavailableError
                        is SignUpResult.OtherError -> UserPhotoScreenState.OtherError
                    }
                    _loadingState.value = LoadingState.NotLoading
                }
            }
        }
    }
}