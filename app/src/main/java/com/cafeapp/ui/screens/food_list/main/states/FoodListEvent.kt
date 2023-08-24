package com.cafeapp.ui.screens.food_list.main.states

import com.cafeapp.domain.models.Food

sealed class FoodListEvent {
    data class AddFoodToCart(val food: Food) : FoodListEvent()
}