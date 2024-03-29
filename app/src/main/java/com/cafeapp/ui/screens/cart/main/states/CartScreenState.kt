package com.cafeapp.ui.screens.cart.main.states

import com.cafeapp.domain.models.Food

sealed class CartScreenState {

    data class FoodList(val foodList: List<Pair<Food, Int>>) : CartScreenState()

    object EmptyFoodList : CartScreenState()

    object Loading : CartScreenState()

    object NetworkError : CartScreenState()

    object OtherError: CartScreenState()
}