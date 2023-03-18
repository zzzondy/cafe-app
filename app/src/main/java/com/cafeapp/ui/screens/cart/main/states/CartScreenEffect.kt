package com.cafeapp.ui.screens.cart.main.states

sealed class CartScreenEffect {
    object ShowNetworkErrorSnackBar : CartScreenEffect()

    data class NavigateToMakeOrderScreen(val selectedItemsIds: List<Long>, val total: Int) : CartScreenEffect()
}