package com.cafeapp.data.cart.remote.states

import com.cafeapp.data.cart.remote.models.CartRemoteFood

sealed class RemoteObtainingCartResult {
    data class Success(val foodList: List<Pair<CartRemoteFood, Int>>) : RemoteObtainingCartResult()
    object NetworkError : RemoteObtainingCartResult()
    object OtherError : RemoteObtainingCartResult()
}
