package com.cafeapp.ui.screens.profile.signUp_flow

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.cafeapp.ui.screens.profile.signUp_flow.models.SignUpParams
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.ByteArrayOutputStream
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

    fun updatePhotoUri(photoUri: Uri?, context: Context) {
        val data = if (photoUri != null) {
            val inputStream = context.contentResolver.openInputStream(photoUri)
            val baos = ByteArrayOutputStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            baos.toByteArray()
        } else {
            null
        }

        signUpParams = signUpParams.copy(photoUri = data)
    }
}