package com.cafeapp.ui.screens.profile.signUp_flow

import androidx.lifecycle.ViewModel
import com.cafeapp.ui.screens.profile.signUp_flow.models.SignUpParams
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpSharedViewModel @Inject constructor() :
    ViewModel() {
    var signUpParams = SignUpParams()

    fun updateEmailAndPassword(email: String, password: String) {
        signUpParams = signUpParams.copy(email = email, password = password)
    }

    fun updateFirstLastNameAndPhone(firstName: String, lastName: String, phoneNumber: String) {
        signUpParams = signUpParams.copy(
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber
        )
    }

    fun updatePhotoUri(photo: ByteArray?) {
        signUpParams = signUpParams.copy(photoUri = photo)
    }
}