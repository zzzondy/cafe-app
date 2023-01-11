package com.cafeapp.ui.screens.profile.signUp_flow

import android.util.Log
import androidx.lifecycle.ViewModel
import com.cafeapp.ui.screens.profile.signUp_flow.models.SignUpParams
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpSharedViewModel @Inject constructor() :
    ViewModel() {
    var signUpParams = SignUpParams()

    fun updateEmailAndPassword(email: String, password: String) {
        signUpParams = SignUpParams(
            email = email,
            password = password,
            firstName = null,
            lastName = null,
            phoneNumber = null,
            photoUri = null
        )

        Log.d("TAG", signUpParams.toString())
    }

    fun updateFirstLastNameAndPhone(firstName: String, lastName: String, phoneNumber: String) {
        signUpParams = SignUpParams(
            email = signUpParams.email,
            password = signUpParams.password,
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber,
            photoUri = null
        )

        Log.d("TAG", signUpParams.toString())
    }

    fun updatePhotoUri(photo: ByteArray?) {
        signUpParams = SignUpParams(
            email = signUpParams.email,
            password = signUpParams.password,
            firstName = signUpParams.firstName,
            lastName = signUpParams.lastName,
            phoneNumber = signUpParams.phoneNumber,
            photoUri = photo
        )
    }
}