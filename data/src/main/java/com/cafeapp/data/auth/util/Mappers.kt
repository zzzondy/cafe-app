package com.cafeapp.data.auth.util

import com.cafeapp.data.auth.remote.models.RemoteUser
import com.cafeapp.data.auth.remote.states.RemoteObtainingUserPhoneNumberResult
import com.cafeapp.domain.auth.states.ObtainingUserPhoneNumberResult
import com.cafeapp.domain.models.User

fun RemoteUser.toDomainUser(): User {
    return User(id, email, photoUrl, displayName)
}

fun RemoteObtainingUserPhoneNumberResult.toDomain(): ObtainingUserPhoneNumberResult =
    when (this) {
        is RemoteObtainingUserPhoneNumberResult.Success -> ObtainingUserPhoneNumberResult.Success(phoneNumber)

        is RemoteObtainingUserPhoneNumberResult.NetworkError -> ObtainingUserPhoneNumberResult.NetworkError

        is RemoteObtainingUserPhoneNumberResult.OtherError -> ObtainingUserPhoneNumberResult.OtherError
    }