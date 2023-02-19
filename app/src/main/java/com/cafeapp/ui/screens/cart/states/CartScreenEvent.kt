package com.cafeapp.ui.screens.cart.states

import com.cafeapp.domain.models.Food

sealed class CartScreenEvent {
    object ScreenEntered : CartScreenEvent()
    object MakeOrderClicked : CartScreenEvent()
    data class ItemSelected(val food: Food) : CartScreenEvent()
    object DeleteSelected : CartScreenEvent()
    data class IncrementItemsCount(val foodId: Long) : CartScreenEvent()
    data class DecrementItemsCount(val foodId: Long) : CartScreenEvent()
    data class DeleteFoodFromCart(val food: Food) : CartScreenEvent()
}
