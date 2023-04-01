package com.cafeapp.domain.auth.states

sealed class ObtainingUserPhoneNumberResult {

    data class Success(val phoneNumber: String) : ObtainingUserPhoneNumberResult()

    object UserNotAuthenticatedError : ObtainingUserPhoneNumberResult()

    object NetworkError : ObtainingUserPhoneNumberResult()

    object OtherError : ObtainingUserPhoneNumberResult()
}
