package com.cafeapp.data.auth.remote.states

sealed class RemoteObtainingUserPhoneNumberResult {
    data class Success(val phoneNumber: String) : RemoteObtainingUserPhoneNumberResult()

    object NetworkError : RemoteObtainingUserPhoneNumberResult()

    object OtherError : RemoteObtainingUserPhoneNumberResult()
}
