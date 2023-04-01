package com.cafeapp.ui.screens.cart.make_order.states

import com.cafeapp.domain.make_order.models.DeliveryMethod
import com.cafeapp.domain.make_order.models.PaymentMethod

sealed class MakeOrderScreenEvent {

    object OnRefreshClicked : MakeOrderScreenEvent()

    object OnCheckoutClicked : MakeOrderScreenEvent()

    data class PaymentMethodSelected(val paymentMethod: PaymentMethod) : MakeOrderScreenEvent()

    data class DeliveryMethodSelected(val deliveryMethod: DeliveryMethod) : MakeOrderScreenEvent()

    data class DeliveryAddressUpdated(val address: String) : MakeOrderScreenEvent()

    object OnBackButtonPressed : MakeOrderScreenEvent()
}