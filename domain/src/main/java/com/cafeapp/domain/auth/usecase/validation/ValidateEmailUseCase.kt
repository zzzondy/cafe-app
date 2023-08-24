package com.cafeapp.domain.auth.usecase.validation

import com.cafeapp.domain.auth.states.validation.ValidationEmailResult
import java.util.regex.Pattern

class ValidateEmailUseCase {

    operator fun invoke(email: String): ValidationEmailResult {
        if (email.isEmpty()) {
            return ValidationEmailResult.EmailIsEmpty
        }

        if (!isValid(email)) {
            return ValidationEmailResult.NotValidEmail
        }

        return ValidationEmailResult.Success
    }

    private fun isValid(email: String): Boolean =
        Pattern.compile(EMAIL_REGEX).matcher(email).matches()

    companion object {
        private const val EMAIL_REGEX = ("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")
    }
}