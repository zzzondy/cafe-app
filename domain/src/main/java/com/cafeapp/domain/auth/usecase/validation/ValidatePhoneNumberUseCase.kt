package com.cafeapp.domain.auth.usecase.validation

import java.util.regex.Pattern

class ValidatePhoneNumberUseCase {

    operator fun invoke(phoneNumber: String): Boolean {
        return isValid(phoneNumber)
    }

    private fun isValid(phoneNumber: String): Boolean =
        Pattern.compile(PHONE_NUMBER_REGEX).matcher(phoneNumber).matches()

    companion object {
        private const val PHONE_NUMBER_REGEX = "[0-9]{10}"
    }
}