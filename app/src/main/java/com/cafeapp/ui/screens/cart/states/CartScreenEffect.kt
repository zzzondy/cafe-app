package com.cafeapp.ui.screens.cart.states

sealed class CartScreenEffect {
    object Initial : CartScreenEffect()
    object ShowNetworkErrorSnackBar : CartScreenEffect()
}