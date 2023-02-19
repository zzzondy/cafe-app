package com.cafeapp.ui.screens.food_list.main.states

sealed class FoodListEvent {
    data class AddFoodToCart(val foodId: Long) : FoodListEvent()
}