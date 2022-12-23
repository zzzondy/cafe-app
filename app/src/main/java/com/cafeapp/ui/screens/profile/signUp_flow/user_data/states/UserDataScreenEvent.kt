package com.cafeapp.ui.screens.profile.signUp_flow.user_data.states

sealed class UserDataScreenEvent {
    data class FirstNameFieldChanged(val firstName: String) : UserDataScreenEvent()
    data class LastNameFieldChanged(val lastName: String) : UserDataScreenEvent()
    data class PhoneNumberFieldChanged(val phoneNumber: String) : UserDataScreenEvent()

    object OnNextButtonClicked : UserDataScreenEvent()
}
