package com.cafeapp.data.food_list.remote.states

import com.cafeapp.data.food_list.remote.models.RemoteFood

sealed class RemoteFoodListResult {
    data class Success(val data: List<RemoteFood>) : RemoteFoodListResult()
    data class OtherError(val e: Throwable) : RemoteFoodListResult()
}
