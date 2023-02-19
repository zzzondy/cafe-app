package com.cafeapp.ui.screens.cart.states

import com.cafeapp.domain.models.Food

sealed class CartScreenState {
    data class FoodList(val foodList: List<Pair<Food, Int>>) : CartScreenState()
    object EmptyFoodList : CartScreenState()
    object IsLoading : CartScreenState()
}