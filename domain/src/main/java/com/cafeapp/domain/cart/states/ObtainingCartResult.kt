package com.cafeapp.domain.cart.states

import com.cafeapp.domain.models.Food

sealed class ObtainingCartResult {
    data class Success(val foodList: List<Pair<Food, Int>>) : ObtainingCartResult()
    object NetworkError : ObtainingCartResult()
    object OtherError : ObtainingCartResult()
}
