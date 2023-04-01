package com.cafeapp.ui.screens.cart.make_order.states

import com.cafeapp.core.util.UiText

sealed class MakeOrderScreenEffect {
    object NavigateBack : MakeOrderScreenEffect()

    object ShowLoading : MakeOrderScreenEffect()

    object HideLoading : MakeOrderScreenEffect()

    object ShowSuccessDialog : MakeOrderScreenEffect()

    data class ShowSnackbar(val message: UiText) : MakeOrderScreenEffect()
}