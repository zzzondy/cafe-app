package com.cafeapp.data.cart.util

import com.cafeapp.data.cart.local.models.LocalCartFood
import com.cafeapp.data.cart.remote.models.CartRemoteFood
import com.cafeapp.data.cart.remote.states.RemoteCartTransactionsResult
import com.cafeapp.data.cart.remote.states.RemoteIncrementResult
import com.cafeapp.data.cart.remote.states.RemoteObtainingCartResult
import com.cafeapp.domain.cart.states.CartTransactionsResult
import com.cafeapp.domain.cart.states.IncrementResult
import com.cafeapp.domain.cart.states.ObtainingCartResult
import com.cafeapp.domain.models.Food

fun RemoteCartTransactionsResult.toDomain(): CartTransactionsResult {
    return when (this) {
        is RemoteCartTransactionsResult.Success -> CartTransactionsResult.Success
        is RemoteCartTransactionsResult.NetworkError -> CartTransactionsResult.NetworkError
        is RemoteCartTransactionsResult.OtherError -> CartTransactionsResult.OtherError
    }
}

fun RemoteObtainingCartResult.toDomain(): ObtainingCartResult {
    return when (this) {
        is RemoteObtainingCartResult.Success -> ObtainingCartResult.Success(this.foodList.map { pair -> pair.first.toDomain() to pair.second })
        is RemoteObtainingCartResult.NetworkError -> ObtainingCartResult.NetworkError
        is RemoteObtainingCartResult.OtherError -> ObtainingCartResult.OtherError
    }
}

fun RemoteIncrementResult.toDomain(): IncrementResult {
    return when (this) {
        is RemoteIncrementResult.Success -> IncrementResult.Success(this.count)
        is RemoteIncrementResult.NetworkError -> IncrementResult.NetworkError
        is RemoteIncrementResult.OtherError -> IncrementResult.OtherError
    }
}

fun LocalCartFood.toDomainWithCount(): Pair<Food, Int> = this.toDomain() to count
private fun LocalCartFood.toDomain(): Food = Food(id, name, price, description, imageUrl)

fun Food.toLocalCartFood(): LocalCartFood = LocalCartFood(id, name, description, price, imageUrl)

private fun CartRemoteFood.toDomain(): Food = Food(id, name, price, description, imageUrl)